package chess;

public class Knight extends Piece {
    public Knight(String colour, int xPos, int yPos) {
        super(colour, xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        if (super.giveMovePath(destx, desty).length == 0) {
            return new int[][]{};
        } else if ((destx - xPos) * (destx - xPos) + (desty - yPos) * (desty - yPos) == 5) {
            return new int[][]{{destx, desty}};
        }
        return new int[][]{};
    }
}
