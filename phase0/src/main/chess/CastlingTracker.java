package chess;

import java.util.*;

class CastlingTracker {
    List<Corner> castlingStates = new ArrayList<>();
    Chessboard board;

    CastlingTracker(Chessboard board, String castlingState){
        this.board = board;

        for (char c: castlingState.toCharArray()) {
            if(c == 'K') castlingStates.add(Corner.K);
            else if(c == 'Q') castlingStates.add(Corner.Q);
            else if(c == 'k') castlingStates.add(Corner.k);
            else if(c == 'q') castlingStates.add(Corner.q);
        }
        System.out.println("CastlingTracker" + castlingStates);
    }

    void update(){
        for (Corner corner:Corner.values()) {
            if(castlingStates.contains(corner)){
                if(!(board.pieceAt(corner.rookSqs) instanceof Rook)) castlingStates.remove(corner);
                else if(!(board.pieceAt(corner.kingSqs) instanceof King)) castlingStates.remove(corner);
            }
        }

    }

    Set<Coord> validCastleCoords(King king){
        Set<Coord> coords = new HashSet<>();
        for (Corner corner: Corner.color.get(king.color())) {
            if(castlingStates.contains(corner)) coords.add(corner.castlingSqs);
        }

        return coords;
    }

    boolean isEmpty(){
        return castlingStates.isEmpty();
    }

    @Override
    public String toString(){
        if (isEmpty()) return "-";

        StringBuilder returnString = new StringBuilder();
        for (Corner corner: castlingStates) {
            returnString.append(corner.string);
        }
        return returnString.toString();
    }

}

enum Corner {
    K ("K", new Coord("g1"), new Coord("h1"), new Coord("e1")),
    Q ("Q", new Coord("c1"), new Coord("a1"), new Coord("e1")),
    k ("k", new Coord("g7"), new Coord("h8"), new Coord("e8")),
    q ("q", new Coord("c7"), new Coord("a8"), new Coord("e8"));

    final String string;
    final Coord castlingSqs;
    final Coord rookSqs;
    final Coord kingSqs;

    final static EnumMap<playerColor, EnumSet<Corner>> color = new EnumMap<>(playerColor.class);
    static {setupEnumMaps();}

    Corner(String string, Coord castlingSqs, Coord rookSqs, Coord kingSqs){
        this.string = string;
        this.castlingSqs = castlingSqs;
        this.rookSqs = rookSqs;
        this.kingSqs = kingSqs;
    }

    static Coord rookSq(Coord castleSq){
        for (Corner corner: Corner.values()) {
            if(corner.castlingSqs.equals(castleSq)) return corner.rookSqs;
        }
        throw new AssertionError("invalid square to castle to");
    }

    private static void setupEnumMaps(){
        color.put(playerColor.White, EnumSet.of(Corner.K, Corner.Q));
        color.put(playerColor.Black, EnumSet.of(Corner.k, Corner.q));
    }



}


