package chess;

import java.text.MessageFormat;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        Player p1 = new UserTemp("a");
        Player p2 = new UserTemp("b");
        ChessGame game = new ChessGame(new Player[]{p1, p2});

        game.verbose = true;

        String[] moves = new String[] {
                "e2", "e4",
                "e7", "e5",
                "f1", "c4",
                "d7", "d6",
                "d1", "f3",
                "b8", "c6",
                "f3", "f7"
        };

        for (String move: moves) {
            Coord moveC = new Coord(move);
            System.out.println(moveC + " " + move);

            game.selectCoord(moveC);

            System.out.println(game.board);
            System.out.println();
        }

    }
}
