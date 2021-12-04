package player.dao;

import player.entity.Player;

import java.util.List;

/**
* @Description: This is the interface used to do data persistence, data persistence
 * may use a database similar to MySQL, or directly with serialization and deserialization
 * to do file IO, no matter which way, you need to implement the following interface data
 * persistence methods
* @Author: Ang Li
* @Date: 2021/12/5
*/
public interface PlayerDao {

    boolean add(Player player);

    boolean update(Player player);

    Player getByName(String playerName);

    boolean delete(Player player);

    List<Player> getAllPlayers();
}
