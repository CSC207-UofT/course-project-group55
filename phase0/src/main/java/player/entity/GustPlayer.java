package player.entity;

public class GustPlayer extends Player{

    public GustPlayer(String name) {
        super(name);
    }

    @Override
    public boolean isPersistenceNeeded() {
        return false;
    }

    @Override
    public String getPassword(){
        System.out.println("gust has no password!");
        return null;
    }

    @Override
    public void setPassword(String password){
        System.out.println("gust has no password!");
    }
}
