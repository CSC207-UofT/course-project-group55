package player.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import player.dao.PlayerDao;
import player.dao.PlayerDaoFileIoImpl;
import player.entity.Player;
import player.entity.PlayerFactory;
import player.entity.PlayerRole;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerControllerImplTest {
    List<Player> savedPlayers;

    @Before
    public void setValue() {
        savedPlayers = new ArrayList<>();
        PlayerDaoFileIoImpl playerDao = new PlayerDaoFileIoImpl();
        for (Player player : PlayerController.getAllPlayers()) {
            playerDao.delete(player);
        }
    }

    @Test
    public void testSignUp() {
        String playerName = "Bob";
        String password = "123456";
        PlayerDao playerDao = new PlayerDaoFileIoImpl();
        assertNull(playerDao.getByName(playerName));

        PlayerController.singUp(playerName, password);
        assertEquals(playerName, playerDao.getByName(playerName).getName());
        assertEquals(password, playerDao.getByName(playerName).getPassword());
    }

    @Test
    public void testSignIn() {
        assertFalse(PlayerController.signIn("Bob", null));
        assertFalse(PlayerController.signIn(null, "123456"));

        String playerName = "Bob";
        String password = "123456";
        PlayerController.singUp(playerName, password);
        assertTrue(PlayerController.signIn(playerName, password));
    }

    @Test
    public void testUpdate() {
        Player bob = PlayerFactory.newPlayer("Bob", PlayerRole.Common);
        bob.setPassword("123456");
        PlayerDaoFileIoImpl playerDao = new PlayerDaoFileIoImpl();
        playerDao.add(bob);

        bob.setPassword("123456abc");
        PlayerController.update(bob);
        assertEquals(bob, playerDao.getByName("Bob"));
    }

    @Test
    public void testGetAllPlayers() {
        int playerNums = 5;
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < playerNums; i++) {
            String playerName = "player-" + i;
            String password = "abc-" + i;
            PlayerController.singUp(playerName, password);

            Player player = PlayerFactory.newPlayer(playerName, PlayerRole.Common);
            player.setPassword(password);
            playerList.add(player);
        }
        assertEquals(playerList, PlayerController.getAllPlayers());
    }

    @Test
    public void testChangePassword() {
        String playerName = "Bob";
        String password = "123456";
        PlayerController.singUp(playerName, password);
        assertTrue(PlayerController.signIn(playerName, password));

        String newPassword = "654321abc";
        PlayerController.changePassword(playerName, newPassword);
        assertFalse(PlayerController.signIn(playerName, password));
        assertTrue(PlayerController.signIn(playerName, newPassword));
    }

    @Test
    public void testChangeName() {
        String playerName = "Bob";
        String password = "123456";
        PlayerController.singUp(playerName, password);
        assertTrue(PlayerController.signIn(playerName, password));

        String newName = "Bob-NewName";
        PlayerController.changeName(playerName, newName);
        assertFalse(PlayerController.signIn(playerName, password));
        assertTrue(PlayerController.signIn(newName, password));
    }


    @After
    public void cleanValue() {
        PlayerDaoFileIoImpl playerDao = new PlayerDaoFileIoImpl();
        for (Player player : PlayerController.getAllPlayers()) {
            playerDao.delete(player);
        }
    }


}
