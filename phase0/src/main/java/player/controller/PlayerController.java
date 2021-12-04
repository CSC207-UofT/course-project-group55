package player.controller;

import player.entity.Player;
import player.entity.PlayerFactory;
import player.entity.PlayerRole;
import player.service.PlayerService;
import player.service.PlayerServiceImpl;

import java.util.List;
import java.util.Objects;

public class PlayerController {
    private static PlayerService playerService;
    static {
        playerService = new PlayerServiceImpl();
    }

    public static boolean signIn(String playerName, String password){
        Player player = playerService.getByName(playerName);
        if (player != null && Objects.equals(player.getPassword(), password)){
            return true;
        }
        return false;
    }

    public static boolean singUp(String playerName, String password){
        Player player = PlayerFactory.newPlayer(playerName, PlayerRole.Common);
        player.setPassword(password);
        return playerService.add(player);
    }

    public static List<Player> getAllPlayers(){
        return playerService.getAllPlayers();
    }

    public static boolean changePassword(String playerName, String newPassword){
        return playerService.changePassword(playerName, newPassword);
    }

    public static boolean changeName(String playerName, String newName){
        return playerService.changeName(playerName, newName);
    }
}
