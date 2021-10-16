import java.util.HashMap;
import java.util.Map;

public class Player {
    String name;
    int numWins;
    int numLosses;
    int numStalemates;
    Map<String, Integer> enemyPiecesTaken;

    public Player(String name) {
        this.name = name;
        this.numWins = 0;
        this.numLosses = 0;
        this.numStalemates = 0;
        this.enemyPiecesTaken = new HashMap<String, Integer>();
        this.enemyPiecesTaken.put("King", 0);
        this.enemyPiecesTaken.put("Queen", 0);
        this.enemyPiecesTaken.put("Rook", 0);
        this.enemyPiecesTaken.put("Knight", 0);
        this.enemyPiecesTaken.put("Bishop", 0);
        this.enemyPiecesTaken.put("Pawn", 0);
    }
}
