package chess;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece implements Cloneable{
    protected playerColor color;
    public playerColor color(){
        return color;
    }

    Set<Coord> legalMoveSet(Coord pieceAt, Chessboard board){
        Set<Coord> legalMoves = new HashSet<>();
        for (Coord direction: moveDirection()) {
            for (int i = 1; true; i++) {
                Coord moveTo = pieceAt.add(direction.multiply(i));
                if(!board.coordInBoard(moveTo) || board.isAlliedPiece(moveTo)) break;
                else legalMoves.add(moveTo);
                if(board.hasPieceAt(moveTo)) break;
            }
        }
        return legalMoves;
    }

    abstract Set<Coord> moveDirection();


    /**
     * @return      the name of the subclass. "Pawn" if it's a Pawn obj, etc.
     */
    public String pieceName() {
        String[] classInfo = getClass().getName().split( "\\.");
        return classInfo[classInfo.length - 1];
    }

    /**
     * @return      corresponding FEN character of the current Piece.
     */
    public char FENChar(){
        String[] classInfo = getClass().getName().split( "\\.");
        char FENChar = classInfo[classInfo.length - 1].toCharArray()[0];
        if(color == playerColor.White) FENChar = Character.toUpperCase(FENChar);
        else FENChar = Character.toLowerCase(FENChar);
        return FENChar;
    }

    /** @return  string rep of Piece obj. Also affects System.out.print */
    @Override
    public String toString() {
        return getClass().getName() + MessageFormat.format(
                " {0}", color);
    }

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Error occured while trying to clone Piece Obj");
        }
    }
}


/* ************************************************************************** */


class King extends Piece{

    public King(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0}, {0, 1}, {-1, 1}, {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
        movementUnits.addAll(newUnits);

        return movementUnits;
    }

    @Override
    Set<Coord> legalMoveSet(Coord pieceAt, Chessboard board){
        Set<Coord> legalMoves = new HashSet<>();

        for (Coord direction: moveDirection()) {
            Coord moveTo = pieceAt.add(direction);
            if (board.coordInBoard(moveTo) && !board.isAlliedPiece(moveTo) && !board.isCoordAttacked(moveTo)) {
                legalMoves.add(moveTo);
            }
        }

        return legalMoves;
    }

}



class Queen extends Piece{

    public Queen(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0}, {0, 1}, {-1, 1}, {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) newUnits.add(direction.multiply(-1));
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Bishop extends Piece{

    public Bishop(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {-1, 1}, {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) newUnits.add(direction.multiply(-1));
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Rook extends Piece{

    public Rook(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0}, {0, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) newUnits.add(direction.multiply(-1));
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Knight extends Piece{

    public Knight(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){

        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 2},
                {2, 1},
                {-1, 2},
                {-2, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
        movementUnits.addAll(newUnits);

        return movementUnits;
    }

    @Override
    Set<Coord> legalMoveSet(Coord pieceAt, Chessboard board){
        Set<Coord> legalMoves = new HashSet<>();
        for (Coord direction: moveDirection()) {
            Coord moveTo = pieceAt.add(direction);
            if (board.coordInBoard(moveTo) && !board.isAlliedPiece(moveTo)) legalMoves.add(moveTo);
        }
        return legalMoves;
    }

    @Override
    public char FENChar(){
        char FENChar = 'N';
        if (color != playerColor.White) FENChar = Character.toLowerCase(FENChar);
        return FENChar;
    }
}


class Pawn extends Piece{

    public Pawn(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){
        int direction = (color == playerColor.White) ? 1: -1;
        return Coord.Coords(new int[][]{
                {0, direction}
        });
    }

    Coord primaryMoveDirection(){
        int direction = (color == playerColor.White) ? 1: -1;
        return new Coord(0, direction);
    }

    Set<Coord> takeDirection(){
        int direction = (color == playerColor.White) ? 1: -1;

        return Coord.Coords(new int[][]{
                {1, direction}, {-1, direction}
        });
    }


    @Override
    Set<Coord> legalMoveSet(Coord pieceAt, Chessboard board){
        Set<Coord> legalMoves = new HashSet<>();
        Coord moveTo = pieceAt.add(primaryMoveDirection());

        // See if pawn can move forward
        int squaresForward = (atStartingSquare(pieceAt, board)) ? 2 : 1;
        for (int i = 1; i <= squaresForward  ; i++) {
            Coord moveTwo = pieceAt.add(primaryMoveDirection().multiply(i));
            if(board.coordInBoard(moveTwo) && !board.hasPieceAt(moveTwo)) legalMoves.add(moveTwo);
        }

        // To test if any taking move is legal
        for (Coord takeDir: takeDirection()) {
            Coord takeTo = pieceAt.add(takeDir);
            if(board.coordInBoard(takeTo) && (board.isEnemyPiece(takeTo) || board.isEnPassantSquare(takeTo))) {
                legalMoves.add(takeTo);
            }
        }

        return legalMoves;
    }

    boolean atStartingSquare(Coord pieceAt, Chessboard board){
        return !board.coordInBoard(pieceAt.subtract(primaryMoveDirection().multiply(2)));
    }


}


class Edge extends Piece{

    public Edge(playerColor color){
        this.color = null;
    }

    Set<Coord> moveDirection(){

        return new HashSet<>();
    }
}