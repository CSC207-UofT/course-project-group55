package chess;

import java.text.MessageFormat;
import java.util.*;

class ChessCheckTracker {
    private final Chessboard board;
    private final playerColor kingColor;
    private Map<Coord, LineOfSight> lineOfSights;
    private Coord currKingCoord;

    ChessCheckTracker(Chessboard board){
        this.board = board;
        kingColor = board.currColor();

        currKingCoord = alliedKingCoord();
        generateNewLineOfSights();

    }

    private void generateNewLineOfSights(){
        this.lineOfSights = new HashMap<>();
        for (Coord direction: knightDirections()) {
            Coord moveTo = direction.add(currKingCoord);
            if(board.isEnemyPiece(moveTo) && board.pieceAt(moveTo) instanceof Knight){
                List<Coord> line = new ArrayList<>();
                line.add(moveTo);
                lineOfSights.put(moveTo, generateLineOfSight(line));
            }
        }

        for (Coord direction: queenDirections()) {
            generateLineOfSightsInDirection(direction);
        }

    }

    /**
     * Scans all coords in the specified direction, and if any piece on that line can potentially move to the king,
     * create a LineOfSight obj
     * @param direction
     */
    private void generateLineOfSightsInDirection(Coord direction){
        List<Coord> lineOfCoords = new ArrayList<>();
        for (int i = 1; true; i++) {
            Coord moveTo = direction.multiply(-i).add(currKingCoord);
            System.out.println("Checking: " + moveTo);
            lineOfCoords.add(moveTo);
            if(!board.coordInBoard(moveTo)) break;
            else if(!board.isEnemyPiece(moveTo) || board.pieceAt(moveTo) instanceof King);
            else if(board.pieceAt(moveTo) instanceof Pawn){
                Pawn pawn = (Pawn) board.pieceAt(moveTo);
                if(i == 1 && pawn.takeDirection().contains(direction.multiply(-1))){
                    lineOfSights.put(moveTo, generateLineOfSight(new ArrayList<>(lineOfCoords)));
                }
            }else if(pieceMovesInDirection(moveTo, direction)){
                lineOfSights.put(moveTo, generateLineOfSight(new ArrayList<>(lineOfCoords)));
                System.out.println(lineOfSights);
            }
        }
    }

    private boolean pieceMovesInDirection(Coord pieceCoord, Coord direction){
        return board.pieceAt(pieceCoord).moveDirection().contains(direction);
    }

    private LineOfSight generateLineOfSight(List<Coord> line){
        Collections.reverse(line);
        System.out.println("UHHHHHH: " + board.pieceAt(line.get(0)) + line);
        return new LineOfSight(board.pieceAt(line.get(0)), line);

    }

    void update(ChessTurn move, boolean isEnPassant){
        if(board.currColor() != kingColor)
            throw new AssertionError("checkTracker with wrong color was called");

        if(board.pieceAt(move.to) instanceof King) {
            currKingCoord = move.to;
            generateNewLineOfSights();
        }
        else{
            currKingCoord = alliedKingCoord();
            //TODO: (OPTIMIZE) Check If any two of the lines are the same and don't repeat
            updateLineOfSightTo(move.to);
            updateLineOfSightTo(move.from);
            if(isEnPassant){
                if(board.pieceAt(move.to) instanceof Pawn) {
                    Pawn movedPawn = (Pawn) board.pieceAt(move.to);
                    Coord enPassantSquare = move.to.subtract(movedPawn.primaryMoveDirection());
                    updateLineOfSightTo(enPassantSquare);
                }else throw new AssertionError("Non-Pawn Piece activated En Passant");
            }
        }
    }

    private void updateLineOfSightTo(Coord pieceCoord){
        Coord pieceToKingVec = currKingCoord.subtract(pieceCoord);

        if(knightDirections().contains(pieceToKingVec) && board.pieceAt(pieceCoord) instanceof Knight){
            List<Coord> line = new ArrayList<>();
            line.add(pieceCoord);
            generateLineOfSight(line);
            return;
        }

        for (Coord direction : queenDirections()) {
            if (direction.isSameDirection(pieceToKingVec)) {
                System.out.println("updateLOS to: " + pieceCoord);

                generateLineOfSightsInDirection(direction);
                System.out.println("Line of Sights Map: " + lineOfSights);
            }
        }
    }

    private Coord alliedKingCoord(){
        for (Coord coord: board.getBoard().keySet()) {
            if(board.pieceAt(coord) instanceof King && board.pieceAt(coord).color() == kingColor) return coord;
        }
        throw new AssertionError("Missing King Piece on the Board");
    }

    private Set<Coord> queenDirections(){
        Queen dummyQueen = new Queen(playerColor.White);
        return dummyQueen.moveDirection();
    }

    private Set<Coord> knightDirections(){
        Knight dummyKnight = new Knight(playerColor.White);
        return dummyKnight.moveDirection();
    }

    boolean isAlliedKingChecked(){
        for(LineOfSight line: lineOfSights.values()){
            if(line.isKingInSight(board)){
                return true;
            }
        }
        return false;
    }


    /**
     * For the sole purpose of checking if a certain square is a square that the king can move into.
     * @param coord
     * @return
     */
    boolean isCoordAttacked(Coord coord){
        for (Coord direction: knightDirections()) {
            Piece piece = board.pieceAt(coord.add(direction));
            if(piece instanceof Knight && piece.color != kingColor){
                return true;
            }
        }
        for (Coord direction: queenDirections()) {
            for (int i = 1; true; i++) {
                Coord pieceCoord = currKingCoord.add(direction.multiply(i));
                if(board.coordInBoard(pieceCoord) || board.isAlliedPiece(pieceCoord)) break;
                else if(i == 1 && board.pieceAt(pieceCoord) instanceof King){
                    return true;
                }
                else if(i == 1 && board.pieceAt(pieceCoord) instanceof Pawn){
                    Pawn pawn = (Pawn) board.pieceAt(pieceCoord);
                    if(pawn.takeDirection().contains(direction.multiply(-1))){
                        return true;
                    }
                }else if(pieceMovesInDirection(pieceCoord, direction)){
                    return true;
                }
            }
        }

        return false;
    }

}


class LineOfSight {
    Piece pieceWithSight;
    List<Coord> lineOfSight;

    LineOfSight(Piece piece, List<Coord> line){
        pieceWithSight = piece;
        lineOfSight = line;
    }

    boolean isKingInSight(Chessboard board){
        for (int i = 1; i < lineOfSight.size(); i++) {
            if(!board.hasPieceAt(lineOfSight.get(i))) return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return MessageFormat.format("{0}: {1}", pieceWithSight, lineOfSight);
    }


}

