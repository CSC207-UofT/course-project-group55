package chess;

public class Main {
    private ChessGame game;

    public static void main(String[] args) {

        Player p1 = new UserTemp("a");
        Player p2 = new UserTemp("b");
        ChessGame game = new ChessGame(new Player[]{p1, p2});

        game.verbose = true;

        String[] scholarsMate = new String[] {
                "e2", "e4",
                "e7", "e5",
                "f1", "c4",
                "d7", "d6",
                "d1", "f3",
                "b8", "c6",
                "f3", "f7"
        };

        String[] enPassant = new String[] {
                "e2", "e4",
                "g8", "f6",
                "e4", "e5",
                "d7", "d5",
                "e5", "d6",
        };

        String[] bongCloudExtended = new String[] {
                "e2", "e4",
                "e7", "e5",
                "e1", "e2",
                "e8", "e7",
                "d2", "d3",
                "d8", "e8",
                "c1", "g5",
                "e7", "d6",
        };

        for (String move: bongCloudExtended) {
            Coord moveC = new Coord(move);
            System.out.println(moveC);

            game.selectCoord(moveC);
            game.confirmTurn();

            System.out.println("Current FEN: " + game.board.FEN());
            if (game.board.isKingChecked()){
                System.out.println("Check!");
            }
            System.out.println();

        }



    }
}
