package chess;

import java.text.MessageFormat;
import java.util.*;

class ChessCheckTracker {
    private final Chessboard board;
    private final playerColor kingColor;
    private Map<Coord, LineOfSight> lineOfSights;
    private Coord currKingCoord;

    private boolean verbose = false;

    ChessCheckTracker(Chessboard board){
        this.board = board;
        kingColor = board.currColor();

        currKingCoord = alliedKingCoord();
        System.out.println("Generating new CheckTrackers:");
        generateNewLineOfSights();


    }

    void generateNewLineOfSights(){
        if(verbose) System.out.println("CT:genNLOSs");

        currKingCoord = alliedKingCoord();

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

        if(!"{}".equals(lineOfSights.toString())){
            if(verbose) System.out.println("King color: " + kingColor);
            if(verbose) System.out.println("curr King Coord: " +  currKingCoord);
            if(verbose) System.out.println("LOS: " + lineOfSights);
        }

    }

    /**
     * Scans all coords in the specified direction, and if any piece on that line can potentially move to the king,
     * create a LineOfSight obj
     * @param direction
     */
    private void generateLineOfSightsInDirection(Coord direction){
        if(verbose) System.out.println("CT:genLOSInDir; Direction: " + direction.multiply(-1));

        List<Coord> lineOfCoords = new ArrayList<>();
        for (int i = 1; true; i++) {
            Coord moveTo = direction.multiply(-i).add(currKingCoord);
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
            }
        }

    }

    private boolean pieceMovesInDirection(Coord pieceCoord, Coord direction){
        return board.pieceAt(pieceCoord).moveDirection().contains(direction);
    }

    private LineOfSight generateLineOfSight(List<Coord> line){
        Collections.reverse(line);
        return new LineOfSight(board.pieceAt(line.get(0)), line);

    }

    /**
     * Called whenever board.movePiece is called.
     * @param move          to update lineOfSights
     * @param isEnPassant   true if move was an enPassant
     */
    void update(ChessTurn move, boolean isEnPassant){
        if(verbose) System.out.println("CCT.Update" + kingColor + "; LOS:" + lineOfSights);

        if(board.currColor() != kingColor)
            throw new AssertionError("checkTracker with wrong color was called");

        currKingCoord = alliedKingCoord();
        if(!(board.pieceAt(move.to) instanceof King)) {
            updateLineOfSightTo(move.to);
            if(!move.to.subtract(currKingCoord).isSameDirection(move.from.subtract(currKingCoord)))
                updateLineOfSightTo(move.from);
            if(isEnPassant){
                if(board.pieceAt(move.to) instanceof Pawn) {
                    Pawn movedPawn = (Pawn) board.pieceAt(move.to);
                    Coord enPassantSquare = move.to.subtract(movedPawn.primaryMoveDirection());
                    if(!move.to.subtract(currKingCoord).isSameDirection(enPassantSquare.subtract(currKingCoord)))
                        updateLineOfSightTo(enPassantSquare);
                }else throw new AssertionError("Non-Pawn Piece activated En Passant");
            }
            if(verbose) System.out.println("updated: " + lineOfSights);
        }

    }

    private void updateLineOfSightTo(Coord pieceCoord){

        if(verbose) System.out.println("CCT.UpdateLOSTo: " + pieceCoord);
        Coord pieceToKingVec = currKingCoord.subtract(pieceCoord);

        if(knightDirections().contains(pieceToKingVec) && board.pieceAt(pieceCoord) instanceof Knight){
            List<Coord> line = new ArrayList<>();
            line.add(pieceCoord);
            generateLineOfSight(line);
            return;
        }

        for (Coord direction : queenDirections()) {
            if (direction.isSameDirection(pieceToKingVec)) {
                generateLineOfSightsInDirection(direction);
            }
        }
        if(!"{}".equals(lineOfSights.toString())){
            if(verbose) System.out.println("King color: " + kingColor);
            if(verbose) System.out.println("curr King Coord: " +  currKingCoord);
            if(verbose) System.out.println("LOS: " + lineOfSights);
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
            if(verbose) System.out.println("CCT.iAKC; kingcolor: " + kingColor);
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
        if(verbose) System.out.println("isCoordAttacked: " + coord);
        for (Coord direction: knightDirections()) {
            Piece piece = board.pieceAt(coord.add(direction));
            if(piece instanceof Knight && piece.color != kingColor){
                if(verbose) System.out.println("Knight attacks " + coord);
                return true;
            }
        }
        for (Coord direction: queenDirections()) {
            for (int i = 1; true; i++) {
                Coord pieceCoord = coord.add(direction.multiply(i));
                if(pieceCoord.equals(currKingCoord));
                else if(!board.coordInBoard(pieceCoord) || board.isAlliedPiece(pieceCoord)) break;
                else if(!board.hasPieceAt(pieceCoord));
                else if(i == 1 && board.pieceAt(pieceCoord) instanceof King){
                    return true;
                }else if(i == 1 && board.pieceAt(pieceCoord) instanceof Pawn){
                    if(verbose) System.out.println("Pawn at " + pieceCoord + " attacks " + coord);
                    Pawn pawn = (Pawn) board.pieceAt(pieceCoord);
                    if(pawn.takeDirection().contains(direction.multiply(-1))){
                        return true;
                    }
                }else if(pieceMovesInDirection(pieceCoord, direction)){
                    if(verbose) System.out.println(coord + " attacked by: " + board.pieceAt(pieceCoord) + " at " + pieceCoord);
                    return true;
                }
                if(board.hasPieceAt(pieceCoord) && !pieceCoord.equals(currKingCoord)) break;
            }
        }

        if(verbose) System.out.println(coord + "not Attacked!");
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
        return MessageFormat.format("LOS {0}: {1}", pieceWithSight, lineOfSight);
    }


}

