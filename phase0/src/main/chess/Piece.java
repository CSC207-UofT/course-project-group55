package chess;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece implements Cloneable{
    protected Chessboard board;
    protected Coord currCoord;
    protected playerColor color;
    public playerColor color(){
        return color;
    }

    Set<Coord> legalMoveSet(){


        Set<Coord> legalMoves = new HashSet<>();
        for (Coord direction: moveDirection()) {
            for (int i = 1; true; i++) {
                Coord moveTo = currCoord.add(direction.multiply(i));
                if(!board.coordInBoard(moveTo) || board.isAlliedPiece(moveTo)) break;
                else legalMoves.add(moveTo);
                if(board.hasPieceAt(moveTo)) break;
            }
        }
        return legalMoves;
    }

    abstract Set<Coord> moveDirection();

    Set<Coord> takeDirection(){
        return moveDirection();
    }

    boolean canMoveTo(Coord coord){
        for (Coord direction: moveDirection()) {
            if(coord.subtract(currCoord).isSameDirection(direction)){
                for (int i = 1; true; i++) {
                    Coord moveTo = currCoord.add(direction.multiply(i));
                    if(!board.coordInBoard(moveTo) || board.isAlliedPiece(moveTo)) return false;
                    else if (moveTo.equals(coord)) return true;
                    else if (board.isEnemyPiece(moveTo)) return false;
                }
            }
        }
        return false;
    }


    void movePiece(ChessTurn move) {
        board.removePiece(currCoord);
        if (board.hasPieceAt(move.to)) board.removePiece(move.to);
        board.placePiece(move.to, this);
        currCoord = move.to;
    }

    /**
     * @return      the name of the subclass. "Pawn" if it's a Pawn obj, etc.
     */
    public String pieceName() {
        String[] classInfo = getClass().getName().split( "\\.");
        return classInfo[classInfo.length - 1];
    }

    /**
     * @return      corresponding FEN character of the current Piece.
     */
    public char FENChar(){
        String[] classInfo = getClass().getName().split( "\\.");
        char FENChar = classInfo[classInfo.length - 1].toCharArray()[0];
        if(color == playerColor.White) FENChar = Character.toUpperCase(FENChar);
        else FENChar = Character.toLowerCase(FENChar);
        return FENChar;
    }

    /** @return  string rep of Piece obj. Also affects System.out.print */
    @Override
    public String toString() {
        return getClass().getName() + MessageFormat.format(
                " {0} at {1}", color, currCoord);
    }

    @Override
    public Piece clone() {
        try {
            return (Piece) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Error occured while trying to clone Piece Obj");
        }
    }
}


/* ************************************************************************** */


class King extends Piece{

    public King(Chessboard board, playerColor color, Coord coord){
        this.board = board;
        this.color = color;
        this.currCoord = coord;
    }

    @Override
    Set<Coord> legalMoveSet(){
        Set<Coord> legalMoves = new HashSet<>();

        for (Coord direction: moveDirection()) {
            Coord moveTo = currCoord.add(direction);
            if (board.coordInBoard(moveTo) && !board.isAlliedPiece(moveTo) && !board.isCoordAttacked(moveTo)) {
                legalMoves.add(moveTo);
            }
        }

        System.out.println("Castling rights: " + board.getCastlingRights(this));

        for (Coord castleTo: board.getCastlingRights(this)){
            System.out.println("!isKingChecked : " + !board.isKingChecked());
            System.out.println("!isCoordAttacked : " + !board.isCoordAttacked(castleTo));
            System.out.println("!isCoordAttacked : " + !board.isCoordAttacked(currCoord.midPoint(castleTo)));
            System.out.println("Are Squares empty: " + board.pieceAt(Corner.rookSq(castleTo)).canMoveTo(currCoord.midPoint(castleTo)));
            if (    !board.isKingChecked() &&
                    !board.isCoordAttacked(castleTo) &&
                    !board.isCoordAttacked(currCoord.midPoint(castleTo)) &&
                    board.pieceAt(Corner.rookSq(castleTo)).canMoveTo(currCoord.midPoint(castleTo))
            ) {
                legalMoves.add(castleTo);
            }
        }

        return legalMoves;
    }

    private boolean isCoordAttacked(Coord coord){
        return board.isCoordAttacked(coord);
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0}, {0, 1}, {-1, 1}, {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
        movementUnits.addAll(newUnits);

        return movementUnits;
    }

    @Override
    void movePiece(ChessTurn move) {
        board.removePiece(currCoord);

        if (board.getCastlingRights(this).contains(move.to)){
            board.placePiece(move.to, this);
            Piece rook = board.pieceAt(Corner.rookSq(move.to));
            if(rook instanceof Rook)
                rook.movePiece(new ChessTurn(Corner.rookSq(move.to), move.to.midPoint(move.from), false));
            else throw new AssertionError("Castling is active but rook is misplaced");
        }

        if (board.hasPieceAt(move.to)) board.removePiece(move.to);
        board.placePiece(move.to, this);
        currCoord = move.to;

        board.currCheckTracker().generateNewLineOfSights();
    }
}


class Queen extends Piece{

    public Queen(Chessboard board, playerColor color, Coord coord){
        this.board = board;
        this.color = color;
        this.currCoord = coord;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0}, {0, 1}, {-1, 1}, {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) newUnits.add(direction.multiply(-1));
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Bishop extends Piece{

    public Bishop(Chessboard board, playerColor color, Coord coord){
        this.board = board;
        this.color = color;
        this.currCoord = coord;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {-1, 1}, {1, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) newUnits.add(direction.multiply(-1));
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Rook extends Piece{

    public Rook(Chessboard board, playerColor color, Coord coord){
        this.board = board;
        this.color = color;
        this.currCoord = coord;
    }

    Set<Coord> moveDirection(){
        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 0}, {0, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) newUnits.add(direction.multiply(-1));
        movementUnits.addAll(newUnits);

        return movementUnits;
    }
}


class Knight extends Piece{

    public Knight(Chessboard board, playerColor color, Coord coord){
        this.board = board;
        this.color = color;
        this.currCoord = coord;
    }

    @Override
    Set<Coord> legalMoveSet(){
        Set<Coord> legalMoves = new HashSet<>();
        for (Coord direction: moveDirection()) {
            Coord moveTo = currCoord.add(direction);
            if (board.coordInBoard(moveTo) && !board.isAlliedPiece(moveTo)) legalMoves.add(moveTo);
        }
        return legalMoves;
    }

    Set<Coord> moveDirection(){

        Set<Coord> movementUnits = Coord.Coords(new int[][]{
                {1, 2},
                {2, 1},
                {-1, 2},
                {-2, 1}
        });
        Set<Coord> newUnits = new HashSet<>();
        for (Coord direction : movementUnits) {
            newUnits.add(direction.multiply(-1));
        }
        movementUnits.addAll(newUnits);

        return movementUnits;
    }

    @Override
    public char FENChar(){
        char FENChar = 'N';
        if (color != playerColor.White) FENChar = Character.toLowerCase(FENChar);
        return FENChar;
    }
}


class Pawn extends Piece{

    public Pawn(Chessboard board, playerColor color, Coord coord){
        this.board = board;
        this.color = color;
        this.currCoord = coord;
    }

    @Override
    Set<Coord> legalMoveSet(){
        Set<Coord> legalMoves = new HashSet<>();
        Coord moveTo = currCoord.add(primaryMoveDirection());

        // See if pawn can move forward
        int squaresForward = (atStartingSquare()) ? 2 : 1;
        for (int i = 1; i <= squaresForward  ; i++) {
            Coord moveTwo = currCoord.add(primaryMoveDirection().multiply(i));
            if(board.coordInBoard(moveTwo) && !board.hasPieceAt(moveTwo)) legalMoves.add(moveTwo);
        }

        // To test if any taking move is legal
        for (Coord takeDir: takeDirection()) {
            Coord takeTo = currCoord.add(takeDir);
            if(board.coordInBoard(takeTo) && (board.isEnemyPiece(takeTo) || board.isEnPassantSquare(takeTo))) {
                legalMoves.add(takeTo);
            }
        }

        return legalMoves;
    }

    Set<Coord> moveDirection(){
        int direction = (color == playerColor.White) ? 1: -1;
        return Coord.Coords(new int[][]{
                {0, direction}
        });
    }

    Coord primaryMoveDirection(){
        int direction = (color == playerColor.White) ? 1: -1;
        return new Coord(0, direction);
    }

    @Override
    Set<Coord> takeDirection(){
        int direction = (color == playerColor.White) ? 1: -1;

        return Coord.Coords(new int[][]{
                {1, direction}, {-1, direction}
        });
    }

    @Override
    void movePiece(ChessTurn move) {
        board.removePiece(currCoord);

        if (board.hasPieceAt(move.to)){
            board.removePiece(move.to);
        }else if (board.isEnPassantSquare(move.to)){
            board.removePiece(move.to.add(primaryMoveDirection().multiply(-1)));
            board.isEnPassant = true;
        }

        board.placePiece(move.to, this);
        currCoord = move.to;

        if(atPromoteSquare()){
            board.promotePawn(this);
        }

    }

    private boolean atStartingSquare(){
        return !board.coordInBoard(currCoord.subtract(primaryMoveDirection().multiply(2)));
    }

    private boolean atPromoteSquare(){
        return !board.coordInBoard(currCoord.add(primaryMoveDirection()));
    }

    boolean isPromoting(Coord moveTo){
        return !board.coordInBoard(moveTo.add(primaryMoveDirection()));
    }
}


class Edge extends Piece{

    public Edge(){
        this.board = null;
        this.color = null;
    }

    Set<Coord> moveDirection(){

        return new HashSet<>();
    }
}