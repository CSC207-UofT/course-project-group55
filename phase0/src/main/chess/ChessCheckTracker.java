package chess;

import java.text.MessageFormat;
import java.util.*;

class ChessCheckTracker {
    private final Chessboard board;
    private final playerColor kingColor;
    private Map<Coord, LineOfSight> lineOfSights;
    private Coord currKingCoord;

    Set<Coord> queenDirections;
    Set<Coord> knightDirections;

    private boolean debug = false;

    ChessCheckTracker(Chessboard board){
        this.board = board;
        kingColor = board.currColor();

        Queen dummyQueen = new Queen(board, kingColor, new Coord(-1,-1));
        queenDirections = dummyQueen.moveDirection();
        dummyQueen = null;

        Knight dummyKnight = new Knight(board, kingColor, new Coord(-1,-1));
        knightDirections = dummyKnight.moveDirection();
        dummyKnight = null;

        currKingCoord = alliedKingCoord();
        if(debug) System.out.println("Generating new CheckTrackers:");
        generateNewLineOfSights();
    }

    void generateNewLineOfSights(){
        if(debug) System.out.println("CT:genNLOSs");

        currKingCoord = alliedKingCoord();

        this.lineOfSights = new HashMap<>();
        for (Coord direction: knightDirections) {
            Coord moveTo = direction.add(currKingCoord);
            if(board.isEnemyPiece(moveTo) && board.pieceAt(moveTo) instanceof Knight){
                List<Coord> line = new ArrayList<>();
                line.add(moveTo);
                lineOfSights.put(moveTo, generateLineOfSight(line));
            }
        }

        for (Coord direction: queenDirections) {
            generateLineOfSightsInDirection(direction);
        }

        if(!"{}".equals(lineOfSights.toString())){
            if(debug) System.out.println("King color: " + kingColor);
            if(debug) System.out.println("curr King Coord: " +  currKingCoord);
            if(debug) System.out.println("LOS: " + lineOfSights);
        }

    }

    /**
     * Scans all coords in the specified direction, and if any piece on that line can potentially move to the king,
     * create a LineOfSight obj
     * @param direction
     */
    private void generateLineOfSightsInDirection(Coord direction){
        if(debug) System.out.println("CT:genLOSInDir; Direction: " + direction.multiply(-1));

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
        if(debug) System.out.println("CCT.Update" + kingColor + "; LOS:" + lineOfSights);

        if(board.currColor() != kingColor)
            throw new AssertionError("checkTracker with wrong color was called");

        currKingCoord = alliedKingCoord();
        if(!(board.pieceAt(move.to) instanceof King)) {
            updateLineOfSightTo(move.to);
            if(!currKingCoord.isCollinear(move.to, move.from))
                updateLineOfSightTo(move.from);
            if(isEnPassant){
                if (!(board.pieceAt(move.to) instanceof Pawn)) {
                    throw new AssertionError("Non-Pawn Piece activated En Passant");
                }
                Pawn movedPawn = (Pawn) board.pieceAt(move.to);
                Coord enPassantSquare = move.to.subtract(movedPawn.primaryMoveDirection());
                if(!(currKingCoord.isCollinear(move.to, enPassantSquare)))
                    updateLineOfSightTo(enPassantSquare);

            }
            if(debug) System.out.println("updated: " + lineOfSights);
        }

    }

    private void updateLineOfSightTo(Coord pieceCoord){

        if(debug) System.out.println("CCT.UpdateLOSTo: " + pieceCoord);
        Coord pieceToKingVec = currKingCoord.subtract(pieceCoord);
        if(knightDirections.contains(pieceToKingVec) && board.pieceAt(pieceCoord) instanceof Knight){
            List<Coord> line = new ArrayList<>();
            line.add(pieceCoord);
            lineOfSights.put(pieceCoord, generateLineOfSight(line));
            return;
        }

        for (Coord direction : queenDirections) {
            if (direction.isSameDirection(pieceToKingVec)) {
                generateLineOfSightsInDirection(direction);
            }
        }
        if(!"{}".equals(lineOfSights.toString())){
            if(debug) System.out.println("King color: " + kingColor);
            if(debug) System.out.println("curr King Coord: " +  currKingCoord);
            if(debug) System.out.println("LOS: " + lineOfSights);
        }
    }

    Coord alliedKingCoord(){
        for (Coord coord: board.getBoard().keySet()) {
            if(board.pieceAt(coord) instanceof King && board.pieceAt(coord).color() == kingColor) return coord;
        }
        throw new AssertionError("Missing King Piece on the Board");
    }

    boolean isAlliedKingChecked(){
        for(LineOfSight line: lineOfSights.values()){
            if(debug) System.out.println("CCT.iAKC; kingcolor: " + kingColor);
            if(line.isKingInSight(board)){
                return true;
            }
        }
        return false;
    }

    List<List<LineOfSight>> pinsAndChecks() {
        List<LineOfSight> checks = new ArrayList<>();
        List<LineOfSight> pins = new ArrayList<>();

        for (LineOfSight line : lineOfSights.values()) {
            int blockCount = line.sightBlockerCount(board);
            if (blockCount == 0) {
                checks.add(line);
            }else if (blockCount == 1){
                pins.add(line);
            }
        }

        List<List<LineOfSight>> pinsAndChecks = new ArrayList<>();
        pinsAndChecks.add(pins);
        pinsAndChecks.add(checks);
        return pinsAndChecks;
    }


    /**
     * For the sole purpose of checking if a certain square is a square that the king can move into.
     * @param coord
     * @return
     */
    boolean isCoordAttacked(Coord coord){
        if(debug) System.out.println("isCoordAttacked: " + coord);
        for (Coord direction: knightDirections) {
            Piece piece = board.pieceAt(coord.add(direction));
            if(piece instanceof Knight && piece.color != kingColor){
                if(debug) System.out.println("Knight attacks " + coord);
                return true;
            }
        }
        for (Coord direction: queenDirections) {
            for (int i = 1; true; i++) {
                Coord pieceCoord = coord.add(direction.multiply(i));
                if(pieceCoord.equals(currKingCoord));
                else if(!board.coordInBoard(pieceCoord) || board.isAlliedPiece(pieceCoord)) break;
                else if(!board.hasPieceAt(pieceCoord));
                else if(i == 1 && board.pieceAt(pieceCoord) instanceof King){
                    return true;
                }else if(i == 1 && board.pieceAt(pieceCoord) instanceof Pawn){
                    if(debug) System.out.println("Pawn at " + pieceCoord + " attacks " + coord);
                    Pawn pawn = (Pawn) board.pieceAt(pieceCoord);
                    if(pawn.takeDirection().contains(direction.multiply(-1))){
                        return true;
                    }
                }else if(pieceMovesInDirection(pieceCoord, direction)){
                    if(debug) System.out.println(coord + " attacked by: " + board.pieceAt(pieceCoord) + " at " + pieceCoord);
                    return true;
                }
                if(board.hasPieceAt(pieceCoord) && !pieceCoord.equals(currKingCoord)) break;
            }
        }

        if(debug) System.out.println(coord + "not Attacked!");
        return false;
    }

}

/**
 * Line of Sight keeps track of the coords starting from the coord of the piece with the sight,
 * until the square right before the king.
 */
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

    int sightBlockerCount(Chessboard board){
        int count = 0;
        for (int i = 1; i < lineOfSight.size(); i++) {
            if(board.hasPieceAt(lineOfSight.get(i))) count++;
        }
        return count;
    }

    Set<Coord> coords(){
        return new HashSet<>(lineOfSight);
    }

    boolean contains(Coord coord){
        return lineOfSight.contains(coord);
    }


    @Override
    public String toString(){
        return MessageFormat.format("LOS {0}: {1}", pieceWithSight, lineOfSight);
    }


}


