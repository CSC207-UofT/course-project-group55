package chess;

public class King extends Piece {
    public King(String colour, int xPos, int yPos) {
        super(colour, xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        if (super.giveMovePath(destx, desty).length == 0) {
            return new int[][] {};
        }

        // This chunk is really awful, will see if I can simplify it later.
        else if (-1 <= destx - xPos & desty == yPos) { // move center left
            return new int[][] {{xPos - 1, yPos}};
        }
        else if (destx - xPos <= 1 & desty == yPos) { // move center right
            return new int[][] {{xPos + 1, yPos}};
        }
        else if (destx == xPos & desty - yPos <= 1) { // move up
            return new int[][] {{xPos, yPos + 1}};
        }
        else if (destx == xPos & -1 <= desty - yPos) { // move down
            return new int[][] {{xPos, yPos - 1}};
        }
        else if (destx - xPos <= 1 & desty - yPos <= 1) { // move top right
            return new int[][] {{xPos + 1, yPos + 1}};
        }
        else if (destx - xPos <= 1 & -1 <= desty - yPos) { // move bottom right
            return new int[][] {{xPos + 1, yPos - 1}};
        }
        else if (-1 <= destx - xPos & -1 <= desty - yPos) { // move bottom left
            return new int[][] {{xPos - 1, yPos - 1}};
        }
        else if (-1 <= destx - xPos & desty - yPos <= 1) { // move top left
            return new int[][] {{xPos - 1, yPos + 1}};
        }

        return new int[][] {};
    }
}