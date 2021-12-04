package player.dao;

import player.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDaoImpl implements PlayerDao {
    public static final String PLAYER_PERSISTENCE_ROOT_PATH = ".chess" + File.separator + "players";
    public static final String PLAYER_PERSISTENCE_SUFFIX_NAME = ".player";
    static {
        File rootFile = new File(PLAYER_PERSISTENCE_ROOT_PATH);
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
    }

    @Override
    public boolean add(Player player) {
        if (player == null || ! player.isPersistenceNeeded()){
            return false;
        }
        try {
            String fileName = PLAYER_PERSISTENCE_ROOT_PATH + File.separator + player.getName() + PLAYER_PERSISTENCE_SUFFIX_NAME;
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(player);
            oos.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Player player) {
        return add(player);
    }

    @Override
    public Player getByName(String playerName) {
        String playerFileName = playerName + PLAYER_PERSISTENCE_SUFFIX_NAME;
        try {
            String fileName = PLAYER_PERSISTENCE_ROOT_PATH + File.separator + playerFileName;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            Player player = (Player) ois.readObject();
            ois.close();
            return player;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean delete(Player player) {
        String playerFileName = player.getName() + PLAYER_PERSISTENCE_SUFFIX_NAME;
        String fileName = PLAYER_PERSISTENCE_ROOT_PATH + File.separator + playerFileName;
        return new File(fileName).delete();
    }

    @Override
    public List<Player> getAllPlayers() {
        ArrayList<Player> playerArrayList = new ArrayList<>();
        File rootFile = new File(PLAYER_PERSISTENCE_ROOT_PATH);
        for (File playerFile : rootFile.listFiles()) {
            playerArrayList.add(getByName(playerFile.getName().split("\\.")[0]));
        }
        return playerArrayList;
    }
}
