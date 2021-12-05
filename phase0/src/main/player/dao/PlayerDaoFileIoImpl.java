package player.dao;

import player.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
* @Description: Use class serialization and file IO to do persistence of user's information
* @Author: Ang Li
* @Date: 2021/12/5
*/
public class PlayerDaoFileIoImpl implements PlayerDao {
    public static final String PLAYER_PERSISTENCE_ROOT_PATH = ".chess" + File.separator + "players";
    public static final String PLAYER_PERSISTENCE_SUFFIX_NAME = ".player";
    static {
        File rootFile = new File(PLAYER_PERSISTENCE_ROOT_PATH);
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
    }

    /**
    * @Description:  Add one user's information to file
    * @Param: [player]
    * @return: boolean
    */
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

    /**
    * @Description:  Update one user's information in file
    * @Param: [player]
    * @return: boolean
    */
    @Override
    public boolean update(Player player) {
        return add(player);
    }

    /**
    * @Description: Get one user from file by username
    * @Param: [playerName]
    * @return: player.entity.Player
    */
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

    /**
    * @Description: Delete one user's information in file
    * @Param: [player]
    * @return: boolean
    */
    @Override
    public boolean delete(Player player) {
        String playerFileName = player.getName() + PLAYER_PERSISTENCE_SUFFIX_NAME;
        String fileName = PLAYER_PERSISTENCE_ROOT_PATH + File.separator + playerFileName;
        return new File(fileName).delete();
    }

    /**
    * @Description: Get all users in file
    * @Param: []
    * @return: java.util.List<player.entity.Player>
    */
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
