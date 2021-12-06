package player.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import player.entity.Player;
import player.entity.PlayerFactory;
import player.entity.PlayerRole;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerDaoImplTest {
    PlayerDaoFileIoImpl playerDao;
    List<Player> savedPlayers;

    @Before
    public void setValue() {
        playerDao = new PlayerDaoFileIoImpl();
        savedPlayers = new ArrayList<>();
        for (Player player : playerDao.getAllPlayers()) {
            playerDao.delete(player);
        }
    }

    @Test
    public void testAdd() {
        Player bob = PlayerFactory.newPlayer("Bob", PlayerRole.Common);
        boolean isBobSaved = playerDao.add(bob);
        assertTrue(isBobSaved);

        Player alice = PlayerFactory.newPlayer("Alice", PlayerRole.Gust);
        boolean isAliceSaved = playerDao.add(alice);
        assertFalse(isAliceSaved);
        savedPlayers.add(bob);
        savedPlayers.add(alice);
    }


    @Test
    public void testUpdate() {
        Player bob = PlayerFactory.newPlayer("Bob", PlayerRole.Common);
        bob.setPassword("123456");
        playerDao.add(bob);

        bob.setPassword("123456abc");
        playerDao.update(bob);
        assertEquals(bob, playerDao.getByName("Bob"));
    }

    @Test
    public void testGetByName() {
        Player cindy = PlayerFactory.newPlayer("Cindy", PlayerRole.Common);
        cindy.setPassword("123456abc");
        playerDao.add(cindy);
        assertEquals(cindy, playerDao.getByName("Cindy"));
        savedPlayers.add(cindy);
    }

    @Test
    public void testDelete() {
        Player bob = PlayerFactory.newPlayer("Bob", PlayerRole.Common);
        playerDao.add(bob);
        playerDao.delete(bob);
        assertNull(playerDao.getByName("Bob"));
    }

    @After
    public void cleanValue() {
        for (Player savedPlayer : savedPlayers) {
            playerDao.delete(savedPlayer);
        }
    }
}
