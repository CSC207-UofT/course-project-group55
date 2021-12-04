package player;

public class PlayerSignInAndSignUpTest {
    public static void main(String[] args) {
        Player player = PlayerFactory.newPlayer("Helen", PlayerRole.Common);
        player.setPassword("123456");

        boolean signIn = PlayerUtil.signIn(player);
        System.out.println("sign in: " + signIn);

        boolean signUp = PlayerUtil.signUp(player);
        System.out.println("sign up: " + signUp);

        boolean signInAgain = PlayerUtil.signIn(player);
        System.out.println("sign in again: " + signInAgain);
    }

}
