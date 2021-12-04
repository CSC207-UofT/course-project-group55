package chess;


/**
 * Generates a piece using the char received based on the
 * Use ONLY for FEN notation; for 2 player chess.
 */
public class PieceFactory{
    public static Piece newPiece(char letter)
            throws InvalidFENException {
        playerColor color;

        if(Character.isUpperCase(letter)) color = playerColor.White;
        else color = playerColor.Black;

        letter = Character.toUpperCase(letter);

        Piece newPiece;
        if      (letter == 'K') newPiece = new King(color);
        else if (letter == 'Q') newPiece = new Queen(color);
        else if (letter == 'R') newPiece = new Rook(color);
        else if (letter == 'N') newPiece = new Knight(color);
        else if (letter == 'B') newPiece = new Bishop(color);
        else if (letter == 'P') newPiece = new Pawn(color);
        else if (letter == 'E') newPiece = new Edge(color);
        else {
            throw new InvalidFENException("Letter in FEN does not correspond to a valid Piece");
        }

        return newPiece;
    }
}
