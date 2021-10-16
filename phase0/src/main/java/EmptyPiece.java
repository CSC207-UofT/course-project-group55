public class EmptyPiece extends Piece {
    public EmptyPiece(int xPos, int yPos) {
        super("None", xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        return new int[][] {};
    }
}
