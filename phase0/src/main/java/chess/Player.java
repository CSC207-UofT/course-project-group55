package chess;

/**
 * An interface for the User obj that will be used to interact with Game.
 */
public interface Player {

    String getName();
    int getELO();
    void addELO(int changeInELO);

    @Override
    boolean equals(Object other);

    int hashCode();

}




class UserTemp implements Player{
    String name;
    int ELO = 1000;

    UserTemp(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public int getELO(){
        return ELO;
    }
    public void addELO(int changeInELO){
        ELO += changeInELO;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof UserTemp) {
            UserTemp otherUser = (UserTemp) other;
            return this.getName().equals(otherUser.getName());
        }
        else return false;
    }


    public int hashCode(){
        char[] charArray = name.toCharArray();
        int hashcode = 0;

        for (char c:
             charArray) {
            hashcode += c - '0';
        }


        return hashcode;
    }
}


