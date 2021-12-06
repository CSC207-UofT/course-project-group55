package player.dao;

import player.entity.Player;

import java.util.List;

/**
 * @Description: This implementation of PlayerDao is based on using MySQL to do persistence
 * @Author: Ang Li
 * @Date: 2021/12/5
 */
public class PlayerDaoMysqlImpl implements PlayerDao {
    @Override
    public boolean add(Player player) {
        // TODO: Use jdbc to connect to MySQL to implement it
        return false;
    }

    @Override
    public boolean update(Player player) {
        // TODO: Use jdbc to connect to MySQL to implement it
        return false;
    }

    @Override
    public Player getByName(String playerName) {
        // TODO: Use jdbc to connect to MySQL to implement it
        return null;
    }

    @Override
    public boolean delete(Player player) {
        // TODO: Use jdbc to connect to MySQL to implement it
        return false;
    }

    @Override
    public List<Player> getAllPlayers() {
        // TODO: Use jdbc to connect to MySQL to implement it
        return null;
    }
}
