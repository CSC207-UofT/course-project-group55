package player;

import chess.PieceRole;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static chess.PieceRole.*;

public abstract class Player implements Serializable, Comparable<Player> {
    private String name;
    private int numWins;
    private int numLosses;
    private int numStalemates;
    private String password;
    private Map<PieceRole, Integer> enemyPiecesTaken;

    protected Player(String name) {
        this.name = name;
        this.enemyPiecesTaken = new HashMap<>();
        for (PieceRole role : values()) {
            this.enemyPiecesTaken.put(role, 0);
        }
    }

    @Override
    public int compareTo(Player player) {
        return this.numWins - player.getNumWins();
    }

    abstract boolean isPersistenceNeeded();

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", numWins=" + numWins +
                ", numLosses=" + numLosses +
                ", numStalemates=" + numStalemates +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return getName().equals(player.getName()) && getPassword().equals(player.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPassword());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumWins(int numWins) {
        this.numWins = numWins;
    }

    public void setNumLosses(int numLosses) {
        this.numLosses = numLosses;
    }

    public void setNumStalemates(int numStalemates) {
        this.numStalemates = numStalemates;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnemyPiecesTaken(Map<PieceRole, Integer> enemyPiecesTaken) {
        this.enemyPiecesTaken = enemyPiecesTaken;
    }

    public int getNumWins() {
        return numWins;
    }

    public int getNumLosses() {
        return numLosses;
    }

    public int getNumStalemates() {
        return numStalemates;
    }

    public String getPassword() {
        return password;
    }

    public Map<PieceRole, Integer> getEnemyPiecesTaken() {
        return enemyPiecesTaken;
    }
}
