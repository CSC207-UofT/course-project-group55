package chess;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class Coord {
    public final int x;
    public final int y;

    static Set<Coord> Coords(String[] coords){
        Set<Coord> generatedCoords = new HashSet<>();
        for (String coord: coords) {
            generatedCoords.add(new Coord(coord));
        }
        return generatedCoords;


    }

    public static Set<Coord> Coords(int[][] coords){
        Set<Coord> generatedCoords = new HashSet<>();
        for (int[] coord: coords) {
            generatedCoords.add(new Coord(coord[0], coord[1]));
        }
        return generatedCoords;
    }

    /**
     * Construct a 2D vector to be used as coordinates on a chess board
     * @param x     x-coordinate
     * @param y     y-coordinate
     */
    public Coord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Coord(String PGN){
        char[] coordSetup = PGN.toCharArray();

        x = Character.toLowerCase(coordSetup[0]) - 'a';
        y = coordSetup[1] - '1';
    }

    /** @return an ArrayList containing this.x and this.y  */
    public int[] coords(){
        return new int[]{x, y};
    }

    /**
     * @param axis  input 'x' or 'y'
     * @return this.x or this.y
     * */
    public int coord(char axis){
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('x', this.x);
        map.put('y', this.y);

        return map.get(axis);
    }

    /**
     * @param other adds the x, y of other to this Coord obj
     * @return returns a new Coord obj with the intended x and y
     */
    public Coord add(Coord other){
        return new Coord(
                x + other.coord('x'),
                y + other.coord('y')
        );
    }

    public Coord add(int x, int y){
        return new Coord(this.x + x, this.y + y);
    }


    /**
     * @param other subtract the x, y of other from this Coord obj
     * @return returns a new Coord obj with the intended x and y
     */
    public Coord subtract(Coord other){
        return new Coord(
                x - other.coord('x'),
                y - other.coord('y')
        );
    }

    public Coord subtract(int x, int y){
        return new Coord(this.x - x, this.y - y);
    }

    /**
     * @param scalar    Multiply both x, y by scalar
     * @return          Coord returns a new Coord obj with the intended x and y
     */
    public Coord multiply(int scalar){
        return new Coord(this.x * scalar, this.y * scalar);
    }

    /**
     * @return      higher number between x and y
     */
    public int maxNorm (){return Integer.max(x, y);}

    public boolean isSameDirection(Coord other){
        return this.rotateCW().dotProduct(other) == 0 && this.dotProduct(other) > 0;
    }

    /**
     * @return      returns a Coord(Vector) that is Clockwise rotated by 90 degrees.
     */
    private Coord rotateCW(){
        return new Coord(y, -x);
    }

    private int dotProduct(Coord other){
        return x * other.x + y * other.y;
    }

    public String letterCoord(){
        return "" + (char)(x + 'a') + (char)(y + '1');
    }

    /**
     * Required to use Coord obj as keys in a Hashmap
     * @param obj   any obj to be compared to this Coord obj
     * @return      true when the x and y coord are equal
     */
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(obj == this) {
            return true;
        }
        if(getClass() != obj.getClass()){
            return false;
        }

        Coord other = (Coord) obj;
        return (this.x == other.x && this.y == other.y);
    }

    /**
     * Required to use Coord obj as keys in a Hashmap
     * @return      hashCode of Coord obj.
     */
    @Override
    public int hashCode(){
        final int SPACE = 1000;
        return (coord('x') * SPACE + coord('y')) * 17;
    }

    /** @return  string rep of Coord obj. */
    @Override
    public String toString() {
        return getClass().getName() + MessageFormat.format(
                ": {0} ({1}, {2})", letterCoord(), x, y);
    }


}
