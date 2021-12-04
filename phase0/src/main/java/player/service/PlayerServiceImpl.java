package player.service;

import player.dao.PlayerDao;
import player.dao.PlayerDaoImpl;
import player.entity.Player;

import java.util.List;

public class PlayerServiceImpl implements PlayerService {
    PlayerDao playerDao;

    {
        playerDao = new PlayerDaoImpl();
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    @Override
    public boolean changePassword(String playerName, String newPassword) {
        Player player = playerDao.getByName(playerName);
        player.setPassword(newPassword);
        return playerDao.update(player);
    }

    @Override
    public boolean changeName(String playerName, String newName) {
        Player player = playerDao.getByName(playerName);
        if (playerDao.delete(player)){
            player.setName(newName);
            return playerDao.add(player);
        }else {
            return false;
        }
    }

    @Override
    public boolean add(Player player) {
        return playerDao.add(player);
    }

    @Override
    public boolean update(Player player) {
        return playerDao.update(player);
    }

    @Override
    public Player getByName(String playerName) {
        return playerDao.getByName(playerName);
    }

    @Override
    public boolean delete(Player player) {
        return playerDao.delete(player);
    }
}
