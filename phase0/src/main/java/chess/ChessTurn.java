package chess;

public class ChessTurn {
    final Coord from;
    final Coord to;
    final boolean isCheck;
    private Piece pieceMoved;

    public ChessTurn(Coord from, Coord to, boolean check){
        this.from = from;
        this.to = to;
        isCheck = check;
    }

    public Coord movedBy(){
        return to.subtract(from);
    }


}