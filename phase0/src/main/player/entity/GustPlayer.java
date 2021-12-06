package player.entity;

/**
* @Description: Gust user, not necessary to be saved
* @Author: Ang Li
* @Date: 2021/12/5
*/
public class GustPlayer extends Player{

    protected GustPlayer(String name) {
        super(name);
    }

    protected GustPlayer(String name, String password) {
        super(name, password);
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
