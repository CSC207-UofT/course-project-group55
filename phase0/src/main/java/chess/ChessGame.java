package chess;

<<<<<<< HEAD
import player.entity.Player;
import player.entity.PlayerFactory;
import player.entity.PlayerRole;
=======

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
>>>>>>> 50f03bca7dd89484ab3b3b7480361d58bbf36053

public class ChessGame {

    Player[] players;
<<<<<<< HEAD
    Chessboard chessboard;
    // Map<player.entity.Player, int[][]> moveHistory;   TODO: Implement move history tracking

    // Can overload constructors for higher player counts. Maybe find a way to do this dynamically?
    public ChessGame(Player player1, Player player2, Chessboard board) {
        this.players = new Player[] {player1, player2};
        this.chessboard = board;
        //this.moveHistory = new Map<player.entity.Player, Integer[2][2]>;
    }

    // This is almost certainly not how the game will work in the finished product.
    // Purely intended to demonstrate the rest of the code.
    public static ChessGame startGame() {
        //initialize a chess game
        Player player1 = PlayerFactory.newPlayer("demo1", PlayerRole.Gust);
        Player player2 = PlayerFactory.newPlayer("demo2", PlayerRole.Gust);
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
=======
    Map<String, playerColor> playerColors;

    final public Chessboard board;
    final ChessTurnTracker currTurn = new ChessTurnTracker();
    Set<Coord> currLegalMoves = new HashSet<>();

    public boolean verbose = false;


    /**
     * Generates an 'n' player Chess Game with custom board size and initial positions
     * @param players   Player that will participate
     * @param FEN       FEN style piece layout
     */
    public ChessGame(Player[] players, String FEN){
        this.players = players;
        playerColors = new HashMap<>();
        for (int i = 0; i < playerColor.values().length; i++) {
            playerColors.put(players[i].getName(), playerColor.values()[i]);
        }
        board = new Chessboard(FEN);
    }

    /**
     * Generates an 'n' player Chess Game with Normal starting position
     * @param players    Player 1
     */
    public ChessGame(Player[] players){
        playerColors = new HashMap<>();
        for (int i = 0; i < playerColor.values().length; i++) {
            playerColors.put(players[i].getName(), playerColor.values()[i]);
        }
        board = new Chessboard();
    }

    public ChessGame(){
        playerColors = new HashMap<>();
        board = new Chessboard();
    }

    public void runGame(Coord coord){
        Coord[] legalMoves;
    }

    /** <b>Primary method of selecting/moving pieces.</b><br>
     *  If no Piece is selected, <br>
     *      - Selecting an allied piece will select that piece <br>
     *      - Anything else will have no effect. <br>
     *  If A piece is currently selected, (Re)selecting: <br>
     *      - the current piece will deselect the piece <br>
     *      - an allied piece will select that new piece <br>
     *      - a legalMove square will make the move <br>
     *      - Anything else will deselect the piece. <br>
     * @param coord     Coordinate to be selected.
     */
    public String selectCoord(Coord coord){
        // If a piece is currently selected
        if(currTurn.isOnSelectState()){
            if(coord == currTurn.moveFrom) {
                deselect();  // If not currPiece, Deselect currently selected piece
                return "fail";
            }
            else if (board.isAlliedPiece(coord)) {
                selectPieceAt(coord); // If not currPiece and AlliedPiece
                return "moveFrom";
            }
            else if (currLegalMoves.contains(coord)) {
                movePiece(coord);
                return "moveTo";
            }
            else {
                deselect();
                return "fail";
            }
        }
        // If no piece is selected, and there is an allied piece on the selected tile
        else if (board.hasPieceAt(coord) && board.isAlliedPiece(coord)) {
            selectPieceAt(coord);
            if(verbose) System.out.println(
                    "Selected piece: " + board.pieceAt(coord) + " " + currTurn.isOnSelectState());
            return "moveFrom";
        }
        // If no piece is selected, and an empty or enemy piece is selected, do nothing.
        return "fail";
    }

    public Set<int[]> getCurrLegalMoves(){
        Set<int[]> moveSet = new HashSet<>();
        for(Coord move: currLegalMoves){
            moveSet.add(move.coords());
        }
        return moveSet;
    }

    /**
     * Sets currTurn.moveFrom and moveTo into null, and assigns a new empty set for currLegalMoves.
     */
    private void deselect(){
        currTurn.deselect();
        currLegalMoves = new HashSet<>();
    }

    /**
     * Sets currTurn.moveFrom to coord, and generates the legal moves of currPiece and puts it into currLegalMoves
     */
    private void selectPieceAt(Coord coord){
        currTurn.moveFrom = coord;
        currLegalMoves = currLegalMoveSet();
        if(verbose) System.out.println("Piece selected; currLegalMoves: " + currLegalMoves);
    }

    Set<Coord> currLegalMoveSet(){
        Piece pieceToMove = board.pieceAt(currTurn.moveFrom);
        if(pieceToMove == null || pieceToMove instanceof Edge) return new HashSet<>();
        return pieceToMove.legalMoveSet(currTurn.moveFrom, board);
    }

    private void movePiece(Coord coord){
        currTurn.moveTo = coord;
        ChessTurn newTurn;
        newTurn = recordMove(currTurn.makeMove());
        board.movePiece(newTurn);
    }

    private ChessTurn recordMove(ChessTurn turn){
        return turn;
    }

    /**
     * For Client to Server Communicating purposes. Left Package-private such that a different class that listens from
     * the Server can access it.
     * @param move      ChessTurn obj that contains the turn number, piece moved from and to information.
     */
    void movePiece(ChessTurn move){

    }

    /**
     * For LAN Multiplayer, when only 1 player has access to this specific instance of ChessGame.
     * @param coord         Coordinate to be pressed
     * @param playerID      Player ID of the player that is requesting the move.
     *                      If it's not the players turn, this method will do nothing.
     */
    public void selectCoord(Coord coord, String playerID){
        if(board.currColor() == playerColors.get(playerID)) selectCoord(coord);
>>>>>>> 50f03bca7dd89484ab3b3b7480361d58bbf36053
    }

    public void selectCoord(int[] coord, String playerID){
        selectCoord(new Coord(coord[0],coord[1]), playerID);
    }

    public Map<Coord, Piece> getBoard(){
        return board.getBoard();
    }


}
