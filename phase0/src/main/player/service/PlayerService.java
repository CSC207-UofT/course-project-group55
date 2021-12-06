package player.service;

import player.entity.Player;

import java.util.List;

/**
 * @Description: This class is used to assemble the basic data persistence
 * operations into some complete business requirements
 * @Author: Ang Li
 * @Date: 2021/12/5
 */
public interface PlayerService {
    List<Player> getAllPlayers();

    boolean changePassword(String playerName, String newPassword);

    boolean changeName(String playerName, String newName);

    boolean add(Player player);

    boolean update(Player player);

    Player getByName(String playerName);

    boolean delete(Player player);
}
