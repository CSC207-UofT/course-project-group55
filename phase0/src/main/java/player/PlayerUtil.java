package player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerUtil {
    // player file name: .chess/players/{Alice.player  Bob.player ... }  ^_^
    public static final String PLAYER_PERSISTENCE_ROOT_PATH = ".chess" + File.separator + "players";
    public static final String PLAYER_PERSISTENCE_SUFFIX_NAME = ".player";
    static {
        File rootFile = new File(PLAYER_PERSISTENCE_ROOT_PATH);
        if (!rootFile.exists()){
            rootFile.mkdirs();
        }
    }

    public static boolean saveToFile(Player player){
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
    // playerName: Bob
    public static Player loadFromFileByPlayerName(String playerName){
        String playerFileName = playerName + PLAYER_PERSISTENCE_SUFFIX_NAME;
        return loadFromFileByFileName(playerFileName);
    }

    // playerFileName: Bob.player
    public static Player loadFromFileByFileName(String playerFileName){
        try {
            String fileName = PLAYER_PERSISTENCE_ROOT_PATH + File.separator + playerFileName;
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            return  (Player) ois.readObject();
        }catch (Exception e){
            // System.out.println(e.getMessage());
            return null;
        }
    }

    // use for display player rankings or load all historical player records
    public static List<Player> getAllPlayerRecords(){
        ArrayList<Player> playerArrayList = new ArrayList<>();
        File rootFile = new File(PLAYER_PERSISTENCE_ROOT_PATH);
        for (File playerFile : rootFile.listFiles()) {
            playerArrayList.add(loadFromFileByFileName(playerFile.getName()));
        }
        return playerArrayList;
    }

    // determine if sign in is successful by persistent data
    public static boolean signIn(Player player){
        if (player == null){
            return false;
        }

        // gust can sign in and play game without records directly
        if (player instanceof GustPlayer){
            return true;
        }
        return player.equals(loadFromFileByPlayerName(player.getName()));
    }

    public static boolean signUp(Player player){
        if (player == null){
            return false;
        }
        if ("".equals(player.getPassword())){
            System.out.println("password is empty!");
            return false;
        }
        return saveToFile(player);
    }


}
