package player.entity;

import chess.PieceRole;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static chess.PieceRole.*;
/**
* @Description: This is an abstraction of the player class, and the two interfaces
 * implemented are for data persistence and sorting of players, respectively
* @Author: Ang Li
* @Date: 2021/12/5
*/
public abstract class Player implements Serializable, Comparable<Player> {
    private UUID uuid;
    private String name;
    private int numWins;
    private int numLosses;
    private int numStalemates;
    private String password;
    private Map<PieceRole, Integer> enemyPiecesTaken;

    protected Player(String name) {
        // This name is the player's nickname and no duplication is allowed
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.enemyPiecesTaken = new HashMap<>();
        for (PieceRole role : values()) {
            this.enemyPiecesTaken.put(role, 0);
        }
    }

    protected Player(String name, String password) {
        this.name = name;
        this.password = password;
        this.uuid = UUID.randomUUID();
        this.enemyPiecesTaken = new HashMap<>();
        for (PieceRole role : values()) {
            this.enemyPiecesTaken.put(role, 0);
        }
    }

    /**
    * @Description: Sort the user by their counts of win times
    * @Param: [player]
    * @return: int
    */
    @Override
    public int compareTo(Player player) {
        return this.numWins - player.getNumWins();
    }

    public abstract boolean isPersistenceNeeded();

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", numWins=" + numWins +
                ", numLosses=" + numLosses +
                ", numStalemates=" + numStalemates +
                '}';
    }

    /**
    * @Description:  This is mainly used to verify the login, if the username and
     * password are the same as the stored player information, it will return true
    * @Param: [o]
    * @return: boolean
    */
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
