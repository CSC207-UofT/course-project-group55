package chess;

public class Pawn extends Piece {
    public Pawn(String colour, int xPos, int yPos) {
        super(colour, xPos, yPos);
    }

    @Override
    public int[][] giveMovePath(int destx, int desty) {
        if (super.giveMovePath(destx, desty).length == 0) {
            return new int[][] {};
        }
        // only works for 8x8 board with 2 players.
        // Need to figure out solution for different board sizes.
        else if (colour.equals("white") & yPos == 1 & desty <= yPos + 2 & destx == xPos) {
            return new int[][] {{xPos, yPos + 1}, {xPos, yPos + 2}};
        }
        else if (colour.equals("white") & desty == yPos + 1 & destx == xPos) {
            return new int[][] {{xPos, yPos + 1}};
        }

        else if (colour.equals("black") & yPos == 6 & desty >= yPos - 2 & destx == xPos) {
            return new int[][] {{xPos, yPos - 1}, {xPos, yPos - 2}};
        }
        else if (colour.equals("black") & desty == yPos - 1 & destx == xPos) {
            return new int[][] {{xPos, yPos - 1}};
        }
        // No behaviour for en passant or taking on the diagonal here.
        // Need to figure that out.

        return new int[][] {};
    }
}
