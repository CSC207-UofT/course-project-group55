package chess;

import player.Player;
import player.PlayerFactory;
import player.PlayerRole;

import static chess.PieceRole.*;

public class ChessGame {
    Player[] players;
    Chessboard chessboard;
    // Map<chess.Player, int[][]> moveHistory;   TODO: Implement move history tracking

    // Can overload constructors for higher player counts. Maybe find a way to do this dynamically?
    public ChessGame(Player player1, Player player2, Chessboard board) {
        this.players = new Player[] {player1, player2};
        this.chessboard = board;
        //this.moveHistory = new Map<chess.Player, Integer[2][2]>;
    }

    // This is almost certainly not how the game will work in the finished product.
    // Purely intended to demonstrate the rest of the code.
    public static ChessGame startGame() {
        //initialize a chess game
        Player player1 = PlayerFactory.newPlayer("demo1", PlayerRole.Gust);
        Player player2 = PlayerFactory.newPlayer("demo2", PlayerRole.Gust);
        Chessboard board = new Chessboard(8, 8);

        PieceRole[] pieceRoles = {Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook};
        for (int i = 0; i < 8; i++) {
            // White pawns
            board.tiles[i][1].setCurrentPiece(PieceFactory.newPiece(Pawn, "white", i, 1));
            // Black pawns
            board.tiles[i][6].setCurrentPiece(PieceFactory.newPiece(Pawn, "black", i, 6));
            // Other white pieces
            board.tiles[i][0].setCurrentPiece(PieceFactory.newPiece(pieceRoles[i],"white", i, 0));
            // Other black pieces
            board.tiles[i][7].setCurrentPiece(PieceFactory.newPiece(pieceRoles[i],"black", i, 7));
        }
        return new ChessGame(player1, player2, board);
    }

    //
    public String endGame() {
        return players[1] + "is the winner";
    }

    // Main to simulate a Scholar's mate.
    public static void main(String[] args) {
        ChessGame game = startGame();
        //Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter ");


        // Scholar's mate moves (NOT WORKING).
        //game.chessboard.movePiece(4, 1, 4, 3);
        //game.chessboard.movePiece(4, 6, 4, 4);
        //game.chessboard.movePiece(5, 0, 2, 3);
        //game.chessboard.movePiece(1, 0, 2, 5);
        //game.chessboard.movePiece(3, 0, 7, 4);
        //game.chessboard.movePiece(6, 7, 5, 5);
        //game.chessboard.movePiece(7, 4, 5, 6);
        //System.out.print(game.chessboard.inCheck(4, 7));
    }
}
