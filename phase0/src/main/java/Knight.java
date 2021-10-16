public class Knight extends Piece {
    public Knight(String colour, int xPos, int yPos) {
        super(colour, xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        if (super.giveMovePath(destx, desty).length == 0) {
            return new int[][] {};
        }
        // This is really ugly but it works.
        else if ((destx == xPos + 1 & desty == yPos + 2) | (destx == xPos + 1 & desty == yPos - 2) |
                (destx == xPos - 1 & desty == yPos + 2) | (destx == xPos - 1 & desty == yPos - 2) |
                (destx == xPos + 2 & desty == yPos + 1) | (destx == xPos + 2 & desty == yPos - 1) |
                (destx == xPos - 2 & desty == yPos + 1) | (destx == xPos - 2 & desty == yPos - 1)) {
            return new int[][] {{destx,desty}};
        }
        return new int[][] {};
    }
}
