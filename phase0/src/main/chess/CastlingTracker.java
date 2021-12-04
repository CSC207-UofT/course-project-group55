package chess;

import java.util.*;

public class CastlingTracker {
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

    void update(){ // Improvement: Somehow make the hardcoded coords not hardcoded
        for (Corner corner:Corner.values()) {
            if(castlingStates.contains(corner)){
                if(!(board.pieceAt(Corner.rookSqs.get(corner)) instanceof Rook)) castlingStates.remove(corner);
                else if(!(board.pieceAt(Corner.kingSqs.get(corner)) instanceof Rook)) castlingStates.remove(corner);
            }
        }

    }

    Set<Coord> validCastleCoords(King king){
        Set<Coord> coords = new HashSet<>();
        for (Corner corner: Corner.color.get(king.color())) {
            coords.add(Corner.castlingSqs.get(corner));
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
    K ("K"),
    Q ("Q"),
    k ("k"),
    q ("q");

    final String string;

    static EnumMap<playerColor, EnumSet<Corner>> color = new EnumMap<>(playerColor.class);
    static EnumMap<Corner, Coord> castlingSqs = new EnumMap<>(Corner.class);
    static EnumMap<Corner, Coord> rookSqs = new EnumMap<>(Corner.class);
    static EnumMap<Corner, Coord> kingSqs = new EnumMap<>(Corner.class);

    static {setupEnumMaps();}

    Corner(String string){
        this.string = string;
    }

    static Coord rookSq(Coord castleSq){
        for (Corner corner: Corner.values()) {
            if(castlingSqs.get(corner).equals(castleSq)) return rookSqs.get(corner);
        }
        throw new AssertionError("invalid square to castle to");
    }

    private static void setupEnumMaps(){
        color.put(playerColor.White, EnumSet.of(Corner.K, Corner.Q));
        color.put(playerColor.Black, EnumSet.of(Corner.k, Corner.q));

        castlingSqs.put(K, new Coord("g1"));
        castlingSqs.put(Q, new Coord("c1"));
        castlingSqs.put(k, new Coord("g7"));
        castlingSqs.put(q, new Coord("c7"));

        rookSqs.put(K, new Coord("h1"));
        rookSqs.put(Q, new Coord("a1"));
        rookSqs.put(k, new Coord("h8"));
        rookSqs.put(q, new Coord("a8"));

        kingSqs.put(K, new Coord("e1"));
        kingSqs.put(Q, new Coord("e1"));
        kingSqs.put(k, new Coord("e8"));
        kingSqs.put(q, new Coord("e8"));
    }

}


