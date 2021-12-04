package chess;


/**
 * Generates a piece using the char received based on the
 * Use ONLY for FEN notation; for 2 player chess.
 */
public class PieceFactory{
    public static Piece newPiece(Chessboard board, char letter, Coord coord){
        playerColor color;

        if(Character.isUpperCase(letter)) color = playerColor.White;
        else color = playerColor.Black;

        letter = Character.toUpperCase(letter);

        Piece newPiece;
        if      (letter == 'K') newPiece = new King(board, color, coord);
        else if (letter == 'Q') newPiece = new Queen(board, color, coord);
        else if (letter == 'R') newPiece = new Rook(board, color, coord);
        else if (letter == 'N') newPiece = new Knight(board, color, coord);
        else if (letter == 'B') newPiece = new Bishop(board, color, coord);
        else if (letter == 'P') newPiece = new Pawn(board, color, coord);
        else if (letter == 'E') newPiece = new Edge();
        else {
            throw new AssertionError("Letter in FEN does not correspond to a valid Piece");
        }

        return newPiece;
    }
}
