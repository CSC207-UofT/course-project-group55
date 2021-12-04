package player;

public class CommonPlayer extends Player{

    protected CommonPlayer(String name) {
        super(name);
    }

    @Override
    boolean isPersistenceNeeded() {
        return true;
    }
}
