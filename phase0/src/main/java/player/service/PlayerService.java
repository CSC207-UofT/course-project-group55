package player.service;

import player.entity.Player;

import java.util.List;

public interface PlayerService {
    List<Player> getAllPlayers();

    boolean changePassword(String playerName, String newPassword);

    boolean changeName(String playerName, String newName);

    boolean add(Player player);

    boolean update(Player player);

    Player getByName(String playerName);

    boolean delete(Player player);
}
