public class Tile {
    Piece currentPiece;

    public Tile() {
        this.currentPiece = new EmptyPiece(0,0);
    }

    public Tile(Piece startPiece) {
        this.currentPiece = startPiece;
    }

    public boolean isEmpty() {
        return !(currentPiece instanceof EmptyPiece);
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void clearPiece() {
        currentPiece = new EmptyPiece(currentPiece.xPos, currentPiece.yPos);
    }

    public void setCurrentPiece(Piece newPiece) {
        currentPiece = newPiece;
    }
}
