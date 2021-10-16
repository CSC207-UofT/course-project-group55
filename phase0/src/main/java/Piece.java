public abstract class Piece {
    String colour;
    int xPos;
    int yPos;

    public Piece(String colour, int xPos, int yPos) {
        this.colour = colour;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public String getColour() {
        return colour;
    }

    public int[][] giveMovePath(int destx, int desty) {
        if (destx == xPos & desty == yPos) {
            return new int[][] {};
        }
        // this only works for 8x8 board. Need to figure out solution for different board sizes.
        // Specific movement behaviour for each piece will be overwritten in subclasses here.
        else if (0 <= destx & destx <= 7 & 0 <= desty & desty <= 7) {
            return new int[][] {};
        }
        return new int[][] {};
    }
}
