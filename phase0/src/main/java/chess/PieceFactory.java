package chess;

public class PieceFactory {
    public static Piece newPiece(PieceRole pieceRole, String colour, int xPos, int yPos){
        return switch (pieceRole){
            case King -> new King(colour, xPos, yPos);
            case Queen -> new Queen(colour, xPos, yPos);
            case Rook -> new Rook(colour, xPos, yPos);
            case Knight -> new Knight(colour, xPos, yPos);
            case Bishop -> new Bishop(colour, xPos, yPos);
            case Pawn -> new Pawn(colour, xPos, yPos);
        };
    }
}
