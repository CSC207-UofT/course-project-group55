package player.controller;

import player.entity.Player;
import player.entity.PlayerFactory;
import player.entity.PlayerRole;
import player.service.PlayerService;
import player.service.PlayerServiceImpl;

import java.util.List;
import java.util.Objects;

/**
 * @Description: This Controller is used to forward business requirements related to
 * the Player, and is a control class similar to the one used to respond to the url
 * @Author: Ang Li
 * @Date: 2021/12/5
 */
public class PlayerController {
    private static PlayerService playerService;

    static {
        playerService = new PlayerServiceImpl();
    }

    /**
     * @Description: Enter the username and password and return whether the login is successful
     * @Param: [playerName, password]
     * @return: boolean
     */
    public static boolean signIn(String playerName, String password) {
        Player player = playerService.getByName(playerName);
        if (player != null && Objects.equals(player.getPassword(), password)) {
            return true;
        }
        return false;
    }

    /**
    * @Description: Enter the username and password, save the user to the database
    * @Param: [playerName, password]
    * @return: boolean
    */
    public static boolean singUp(String playerName, String password) {
        Player player = PlayerFactory.newPlayer(playerName, PlayerRole.Common);
        player.setPassword(password);
        return playerService.add(player);
    }

    /**
    * @Description: Get all saved users
    * @Param: []
    * @return: java.util.List<player.entity.Player>
    */
    public static List<Player> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    /**
    * @Description:  Change the password for one single user
    * @Param: [playerName, newPassword]
    * @return: boolean
    */
    public static boolean changePassword(String playerName, String newPassword) {
        return playerService.changePassword(playerName, newPassword);
    }

    /**
    * @Description:  Change the username for one single user
    * @Param: [playerName, newName]
    * @return: boolean
    */
    public static boolean changeName(String playerName, String newName) {
        return playerService.changeName(playerName, newName);
    }
}
