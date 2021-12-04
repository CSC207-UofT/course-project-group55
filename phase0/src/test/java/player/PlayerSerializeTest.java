package player;

import java.io.*;

public class PlayerSerializeTest {
    public static void main(String[] args) throws Exception {
        Player player = PlayerFactory.newPlayer("player007", PlayerRole.Gust);
        System.out.println(player);

        String fileName = PlayerUtil.PLAYER_PERSISTENCE_ROOT_PATH + File.separator + player.getName() + PlayerUtil.PLAYER_PERSISTENCE_SUFFIX_NAME;
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(player);
        oos.close();


        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        System.out.println((Player) ois.readObject());
    }
}
