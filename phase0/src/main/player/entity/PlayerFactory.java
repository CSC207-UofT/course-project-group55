package player.entity;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/**
* @Description: Use Factory Pattern to get instance of users
* @Author: Ang Li
* @Date: 2021/12/5
*/
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
