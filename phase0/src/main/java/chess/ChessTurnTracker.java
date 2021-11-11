package chess;

class ChessTurnTracker {

    Coord moveFrom;
    Coord moveTo;

    ChessTurnTracker(){
    }

    ChessTurn makeMove(){
        ChessTurn move = new ChessTurn(moveFrom, moveTo, false, false);
        deselect();
        return move;
    }

    void deselect(){
        moveFrom = null;
        moveTo = null;
    }



    boolean isOnSelectState(){
        return moveFrom != null;
    }







}


