public class Bishop extends Piece {
    public Bishop(String colour, int xPos, int yPos) {
        super(colour, xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        if (super.giveMovePath(destx, desty).length == 0) {
            return new int[][] {};
        }
        // diagonal to top right
        else if (Math.abs(destx - xPos) == Math.abs(desty - yPos) & destx - xPos > 0 & desty - yPos > 0) {
            int[][] movePath = new int[destx - xPos][2]; // number of tiles covered will be diff. in destx and xPos
            for (int i = 0; i < destx - xPos; i++) {
                movePath[i] = new int[] {xPos + i, yPos + i};
            }
            return movePath;
        }
        // diagonal to bottom right
        else if (Math.abs(destx - xPos) == Math.abs(desty - yPos) & destx - xPos > 0 & desty - yPos < 0) {
            int[][] movePath = new int[destx - xPos][2];
            for (int i = 0; i < destx - xPos; i++) {
                movePath[i] = new int[] {xPos + i, yPos - i};
            }
            return movePath;
        }
        // diagonal to bottom left
        else if (Math.abs(destx - xPos) == Math.abs(desty - yPos) & destx - xPos < 0 & desty - yPos < 0) {
            int[][] movePath = new int[xPos - destx][2];
            for (int i = 0; i < xPos - destx; i++) {
                movePath[i] = new int[] {xPos - i, yPos - i};
            }
            return movePath;
        }
        // diagonal to top left
        else if (Math.abs(destx - xPos) == Math.abs(desty - yPos) & destx - xPos < 0 & desty - yPos > 0) {
            int[][] movePath = new int[xPos - destx][2];
            for (int i = 0; i < xPos - destx; i++) {
                movePath[i] = new int[] {xPos - i, yPos + i};
            }
            return movePath;
        }

        return new int[][] {};
    }
}
