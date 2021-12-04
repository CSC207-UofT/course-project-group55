package player.entity;

public class CommonPlayer extends Player{

    protected CommonPlayer(String name) {
        super(name);
    }

    @Override
    public boolean isPersistenceNeeded() {
        return true;
    }
}
