package player;

public class PlayerFactory {

    public static Player newPlayer(String name, PlayerRole playerRole){
        switch (playerRole){
            case Gust:
                return new GustPlayer(name);
            default:
                return new CommonPlayer(name);
        }
    }
}
