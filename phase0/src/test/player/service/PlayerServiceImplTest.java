package player.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import player.entity.Player;
import player.entity.PlayerFactory;
import player.entity.PlayerRole;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerServiceImplTest {
    PlayerService playerService;
    List<Player> savedPlayers;

    @Before
    public void setValue(){
        playerService = new PlayerServiceImpl();
        savedPlayers = new ArrayList<>();
        for (Player player: playerService.getAllPlayers()){
            playerService.delete(player);
        }
    }

    @Test
    public void testGetAllPlayers(){
        int playerNums = 5;
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < playerNums; i++) {
            Player player = PlayerFactory.newPlayer("player-" + i, PlayerRole.Common);
            player.setPassword("abc-" + i);
            playerList.add(player);
            playerService.add(player);
        }
        assertEquals(playerList, playerService.getAllPlayers());

        savedPlayers.addAll(playerList);
    }

    @Test
    public void testChangePassword(){
        Player bob = PlayerFactory.newPlayer("Bob", PlayerRole.Common);
        bob.setPassword("123456");
        playerService.add(bob);

        String newPassword = "abc12345abc";
        bob.setPassword(newPassword);

        playerService.changePassword("Bob", newPassword);
        assertEquals(bob, playerService.getByName("Bob"));

        savedPlayers.add(bob);
    }

    @Test
    public void testChangeName(){
        Player bob = PlayerFactory.newPlayer("Bob", "123456",PlayerRole.Common);
        playerService.add(bob);

        String newName = "Bob-NewName";
        bob.setName(newName);

        playerService.changeName("Bob", newName);
        assertEquals(bob, playerService.getByName(newName));

        savedPlayers.add(bob);
    }

    @After
    public void cleanValue(){
        for (Player savedPlayer : savedPlayers) {
            playerService.delete(savedPlayer);
        }
    }
}
