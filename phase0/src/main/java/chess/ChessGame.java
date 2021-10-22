package chess;

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
        Player player1 = new Player("demo1");
        Player player2 = new Player("demo2");
        Chessboard board = new Chessboard(8, 8);

        // White pawns
        board.tiles[0][1].setCurrentPiece(new Pawn("white", 0, 1));
        board.tiles[1][1].setCurrentPiece(new Pawn("white", 1, 1));
        board.tiles[2][1].setCurrentPiece(new Pawn("white", 2, 1));
        board.tiles[3][1].setCurrentPiece(new Pawn("white", 3, 1));
        board.tiles[4][1].setCurrentPiece(new Pawn("white", 4, 1));
        board.tiles[5][1].setCurrentPiece(new Pawn("white", 5, 1));
        board.tiles[6][1].setCurrentPiece(new Pawn("white", 6, 1));
        board.tiles[7][1].setCurrentPiece(new Pawn("white", 7, 1));

        // Black pawns
        board.tiles[0][6].setCurrentPiece(new Pawn("black", 0, 6));
        board.tiles[1][6].setCurrentPiece(new Pawn("black", 1, 6));
        board.tiles[2][6].setCurrentPiece(new Pawn("black", 2, 6));
        board.tiles[3][6].setCurrentPiece(new Pawn("black", 3, 6));
        board.tiles[4][6].setCurrentPiece(new Pawn("black", 4, 6));
        board.tiles[5][6].setCurrentPiece(new Pawn("black", 5, 6));
        board.tiles[6][6].setCurrentPiece(new Pawn("black", 6, 6));
        board.tiles[7][6].setCurrentPiece(new Pawn("black", 7, 6));

        // Other white pieces
        board.tiles[0][0].setCurrentPiece(new Rook("white", 0, 0));
        board.tiles[1][0].setCurrentPiece(new Knight("white", 1, 0));
        board.tiles[2][0].setCurrentPiece(new Bishop("white", 2, 0));
        board.tiles[3][0].setCurrentPiece(new Queen("white", 3, 0));
        board.tiles[4][0].setCurrentPiece(new King("white", 4, 0));
        board.tiles[5][0].setCurrentPiece(new Bishop("white", 5, 0));
        board.tiles[6][0].setCurrentPiece(new Knight("white", 6, 0));
        board.tiles[7][0].setCurrentPiece(new Rook("white", 7, 0));

        // Other black pieces
        board.tiles[0][7].setCurrentPiece(new Rook("black", 0, 7));
        board.tiles[1][7].setCurrentPiece(new Knight("black", 1, 7));
        board.tiles[2][7].setCurrentPiece(new Bishop("black", 2, 7));
        board.tiles[3][7].setCurrentPiece(new Queen("black", 3, 7));
        board.tiles[4][7].setCurrentPiece(new King("black", 4, 7));
        board.tiles[5][7].setCurrentPiece(new Bishop("black", 5, 7));
        board.tiles[6][7].setCurrentPiece(new Knight("black", 6, 7));
        board.tiles[7][7].setCurrentPiece(new Rook("black", 7, 7));

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
