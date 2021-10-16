public class Queen extends Piece {
    public Queen(String colour, int xPos, int yPos) {
        super(colour, xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        if (super.giveMovePath(destx, desty).length == 0) {
            return new int[][] {};
        }

        if (destx == xPos & desty - yPos > 0) { // straight line up vertically
            int[][] movePath = new int[desty - yPos][2]; // number of tiles covered will be diff. in desty and yPos
            for (int i = 0; i < desty - yPos; i++) {
                movePath[i] = new int[] {xPos, yPos + i};
            }
            return movePath;
        }
        else if (destx == xPos & desty - yPos < 0) { // straight line down vertically
            int[][] movePath = new int[yPos - desty][2];
            for (int i = 0; i < yPos - desty; i++) {
                movePath[i] = new int[] {xPos, yPos - i};
            }
            return movePath;
        }
        else if (desty == yPos & destx - xPos > 0) { // straight line right horizontally
            int[][] movePath = new int[destx - xPos][2]; // number of tiles covered will be diff. in destx and xPos
            for (int i = 0; i < destx - xPos; i++) {
                movePath[i] = new int[] {xPos + i, yPos};
            }
            return movePath;
        }
        else if (desty == yPos & destx - xPos < 0) { // straight line left horizontally
            int[][] movePath = new int[xPos - destx][2];
            for (int i = 0; i < xPos - destx; i++) {
                movePath[i] = new int[] {xPos - i};
            }
            return movePath;
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
