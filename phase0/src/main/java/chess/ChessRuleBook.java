package chess;

import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ChessRuleBook {
    Chessboard board;
    ChessTurnTracker currTurn;
    ChessCheckTracker checkTracker;

    ChessRuleBook(Chessboard board, ChessTurnTracker currTurn){
        this.board = board;
        this.currTurn = currTurn;
        checkTracker = new ChessCheckTracker(board, currTurn);
    }

    Set<Coord> currLegalMoveSet(){
        Piece pieceToMove = board.pieceAt(currTurn.moveFrom);
        Set<Coord> legalMoves = new HashSet<>();

        if(pieceToMove == null || pieceToMove instanceof Edge) return legalMoves;

        Set<Coord> moveDirection = pieceToMove.moveDirection();

        if(pieceToMove instanceof Pawn){
            return pawnLegalMoveSet();
        }


        for (Coord direction: moveDirection) {
            int i = 1;
            Coord moveTo;
            do{
                moveTo = currTurn.moveFrom.add(direction.multiply(i));

                // If moving into an allied piece or moving out of board, stop.
                if(!board.coordInBoard(moveTo) || board.isAlliedPiece(moveTo)) break;

                // If Empty square or an Enemy Piece,
                else {
                    // If not a King piece, then no problem
                    if(!(pieceToMove instanceof King)) legalMoves.add(moveTo);
                    // If is a King piece, then check if the landing square is being attacked
                    else if (!checkTracker.isAttacked(moveTo)) legalMoves.add(moveTo);
                    // If moving into an Enemy Piece, stop.
                    if(board.hasPieceAt(moveTo)) break;
                }

                //If the piece can move in a line, proceed to check if the next square in the line is legal
                i++;

            }while(pieceToMove.canMoveInLine());

        }

        return legalMoves;
    }

    private Set<Coord> pawnLegalMoveSet(){
        Piece pieceToMove = board.pieceAt(currTurn.moveFrom);
        Set<Coord> legalMoves = new HashSet<>();
        Set<Coord> moveDirection = pieceToMove.moveDirection();
        for(Coord move: moveDirection) {
            moveDirection.add(move.multiply(2));
        }
        for(Coord move: moveDirection) {
            legalMoves.add(move.add(currTurn.moveFrom));
        }
        return legalMoves;
    }



}


class ChessCheckTracker {
    private Chessboard board;
    private ChessTurnTracker currTurn;
    private Set<LineOfSight> lineOfSights;

    ChessCheckTracker(Chessboard board, ChessTurnTracker currTurn){
        this.board = board;
        this.currTurn = currTurn;
        this.lineOfSights = new HashSet<>();
    }

    private Coord alliedKingCoord(){
        for (Coord coord: board.getBoard().keySet()) {
            if(board.pieceAt(coord) instanceof King && board.pieceAt(coord).color() == board.currColor()){
                return coord;
            }
        }
        throw new AssertionError("Missing King Piece on the Board");
    }

    boolean isAttacked(Coord coord){
        return false;
    }




}


class LineOfSight {
    Piece pieceWithSight;
    List<Coord> lineOfSight = new ArrayList<>();

    LineOfSight(){


    }
}