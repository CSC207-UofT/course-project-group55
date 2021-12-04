package player.entity;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class PlayerFactory {

    public static Player newPlayer(String name, PlayerRole playerRole){
        switch (playerRole){
            case Gust:
                return new GustPlayer(name);
            case Common:
                return new CommonPlayer(name);
            default:
                throw new  ValueException("wrong player role type");
        }
    }
}
