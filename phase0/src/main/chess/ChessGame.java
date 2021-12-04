package chess;

import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class ChessGame {

    Player[] players;
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

    public ChessGame(String[] playerNames){
        playerColors = new HashMap<>();
        for (int i = 0; i < playerColor.values().length; i++) {
            playerColors.put(playerNames[i], playerColor.values()[i]);
        }
        board = new Chessboard();
    }

    public ChessGame(String[] playerNames, String FEN){
        playerColors = new HashMap<>();
        for (int i = 0; i < playerColor.values().length; i++) {
            playerColors.put(playerNames[i], playerColor.values()[i]);
        }
        board = new Chessboard(FEN);
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
    public void selectCoord(Coord coord){
        // If a piece is currently selected
        if(currTurn.isOnSelectState()){
            if(coord == currTurn.moveFrom) deselect();  // If not currPiece, Deselect currently selected piece
            else if (board.isAlliedPiece(coord)) selectPieceAt(coord); // If not currPiece and AlliedPiece
            else if (currLegalMoves.contains(coord)) currTurn.moveTo = coord;
            else deselect();
        }

        // If no piece is selected, and there is an allied piece on the selected tile
        else if (board.hasPieceAt(coord) && board.isAlliedPiece(coord)) {
            selectPieceAt(coord);
            if(verbose) System.out.println(
                    "Selected piece: " + board.pieceAt(coord) + " " + currTurn.isOnSelectState());
        }

        // If no piece is selected, and an empty or enemy piece is selected, do nothing.

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
        return pieceToMove.legalMoveSet();
    }

    void confirmTurn(){
        if(currTurn.moveTo != null) {
            board.movePiece(recordMove(currTurn.makeMove()));
            currLegalMoves = new HashSet<>();
        }
    }

    private ChessTurn recordMove(ChessTurn turn){
        return turn;
    }

    /**
     * For Client to Server Communicating purposes. Left Package-private such that a different class that listens from
     * the Server can access it.
     * @param move      ChessTurn obj that contains the turn number, piece moved from and to information.
     */
    void movePiece(ChessTurn move, String playerName){
        if(playerColors.get(playerName) == board.currColor()) board.movePiece(move);

    }

    /**
     * For LAN Multiplayer, when only 1 player has access to this specific instance of ChessGame.
     * @param coord         Coordinate to be pressed
     * @param playerID      Player ID of the player that is requesting the move.
     *                      If it's not the players turn, this method will do nothing.
     */
    public void selectCoord(Coord coord, String playerID){
        if(board.currColor() == playerColors.get(playerID)) selectCoord(coord);
    }

    public void selectCoord(int[] coord, String playerID){
        selectCoord(new Coord(coord[0],coord[1]), playerID);
    }

    public Map<Coord, Piece> getBoard(){
        return board.getBoard();
    }

    public Set<Coord> getCurrLegalMoves(){
        return new HashSet<>(currLegalMoves);
    }

    /**
     * @return  null if no piece selected, Coord if piece is selected
     */
    public Coord currSelectedPiece(){
        return currTurn.moveFrom;
    }

    public playerColor currColor(){
        return board.currColor();
    }

    public boolean promoting(){
        if(currTurn.moveFrom == null || currTurn.moveTo == null) return false;

        if(board.pieceAt(currTurn.moveFrom) instanceof Pawn) {
            Pawn pawn = (Pawn) board.pieceAt(currTurn.moveFrom);
            return pawn.isPromoting(currTurn.moveTo);
        }
        return false;
    }

    public void promotePawnTo(char piece){
        board.promoteToPiece = piece;
    }

    public boolean isChecked(){
        return board.isKingChecked();
    }

    public Coord kingCoord(){
        return board.currCheckTracker().alliedKingCoord();
    }

    //TODO
    public boolean isGameOver(){
        return false;
    }

    //TODO
    public String gameOverMessage(){
        return "Game Over!";
    }



}

