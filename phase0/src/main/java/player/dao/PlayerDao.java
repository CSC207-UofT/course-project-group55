package player.dao;

import player.entity.Player;

import java.util.List;

public interface PlayerDao {

    boolean add(Player player);

    boolean update(Player player);

    Player getByName(String playerName);

    boolean delete(Player player);

    List<Player> getAllPlayers();
}
