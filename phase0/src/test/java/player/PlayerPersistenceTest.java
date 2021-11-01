package player;

public class PlayerPersistenceTest {
    public static void main(String[] args) {
        Player player = PlayerFactory.newPlayer("Bob", PlayerRole.Common);
        player.setPassword("123456");
        // save
        PlayerUtil.saveToFile(player);

        // load
        Player loadedPlayer = PlayerUtil.loadFromFileByPlayerName(player.getName());
        System.out.println(player);
        System.out.println(loadedPlayer);
        System.out.println(loadedPlayer.equals(player));

        PlayerUtil.saveToFile(PlayerFactory.newPlayer("Alice", PlayerRole.Common));
        PlayerUtil.saveToFile(PlayerFactory.newPlayer("Bob", PlayerRole.Gust));
        PlayerUtil.saveToFile(PlayerFactory.newPlayer("Cindy", PlayerRole.Common));
        System.out.println(PlayerUtil.getAllPlayerRecords());
    }
}
