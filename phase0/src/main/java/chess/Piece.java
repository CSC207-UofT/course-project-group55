package chess;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece implements Cloneable{
    protected playerColor color;
    protected boolean canMoveInLine = true;

    abstract Set<Coord> moveDirection();

    public playerColor color(){
        return color;
    }

    boolean canMoveInLine(){
        return canMoveInLine;
    }

    /**
     * @return      the name of the subclass. "Pawn" if it's a Pawn obj, etc.
     */
    public String pieceName() {return getClass().getName().split( "\\.")[1]; }

    /**
     * @return      corresponding FEN character of the current Piece.
     */
    public char FENChar(){
        char FENChar = getClass().getName().split("\\.")[1].toCharArray()[0];
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
        canMoveInLine = false;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0},
                {0, 1},
                {-1, 1},
                {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Queen extends Piece{

    public Queen(playerColor color){
        this.color = color;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0},
                {0, 1},
                {-1, 1},
                {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
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
                {-1, 1},
                {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
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
                {1, 0},
                {0, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Knight extends Piece{

    public Knight(playerColor color){
        this.color = color;
        canMoveInLine = false;
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
    public char FENChar(){
        char FENChar = 'N';
        if (color != playerColor.White) FENChar = Character.toLowerCase(FENChar);
        return FENChar;
    }
}


class Pawn extends Piece{

    public Pawn(playerColor color){
        this.color = color;
        canMoveInLine = false;
    }

    Set<Coord> moveDirection(){
        int direction = 1;
        if(color == playerColor.Black) direction = -1;

        return Coord.Coords(new int[][]{
                {0, direction}
        });
    }
}


class Edge extends Piece{

    public Edge(playerColor color){
        this.color = null;
        canMoveInLine = false;
    }

    Set<Coord> moveDirection(){

        return new HashSet<>();
    }
}