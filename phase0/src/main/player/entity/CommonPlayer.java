package player.entity;

/**
 * @Description: Normal user, need to be saved
 * @Author: Ang Li
 * @Date: 2021/12/5
 */
public class CommonPlayer extends Player {

    protected CommonPlayer(String name) {
        super(name);
    }

    protected CommonPlayer(String name, String password) {
        super(name, password);
    }

    @Override
    public boolean isPersistenceNeeded() {
        return true;
    }
}
