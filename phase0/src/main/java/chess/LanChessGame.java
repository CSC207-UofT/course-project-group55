package chess;

import lan.*;

import java.io.IOException;
import java.util.Scanner;

public class LanChessGame {

    public void printInfo(ChessGame game){
        System.out.println("Current FEN: " + game.board.FEN());
        if (game.board.isKingChecked()){
            System.out.println("Check!");
        }
        System.out.println();
    }

    private void sendMoves(Client client, ChessGame game) throws IOException {
        String moveF = "";
        Coord moveC;
        String moveT = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a Piece to moveFrom");
        moveF = scanner.nextLine();
        moveC = new Coord(moveF);
        String validPrevious = "";
        String vaildCoord = game.selectCoord(moveC);
        System.out.println("Returned validState is " +vaildCoord);
        while (!vaildCoord.equals("moveTo")) {
            if (vaildCoord.equals("fail")){
                System.out.println("Choose a Piece to moveFrom");
                moveF = scanner.nextLine();
                moveC = new Coord(moveF);
                vaildCoord = game.selectCoord(moveC);
                printInfo(game);
            }
            else if(vaildCoord.equals("moveFrom")){
                System.out.println("Choose a Piece to moveTo");
                moveT = scanner.nextLine();
                moveC = new Coord(moveT);
                vaildCoord = game.selectCoord(moveC);
                printInfo(game);
            }
        }
        client.sendMessage(moveF);
        client.sendMessage(moveT);
    }

    //get data sent by opponent and move them.
    private void receiveMoves(Client client, ChessGame game) throws IOException {
        //get opponent's piece to moveFrom
        String oppMsg = client.receiveMessage();
        String[] moves = oppMsg.split(" ");
        game.selectCoord(new Coord(moves[1]));
        printInfo(game);

        //get opponent's piece to moveTo
        oppMsg = client.receiveMessage();
        moves = oppMsg.split(" ");
        game.selectCoord(new Coord(moves[1]));
        printInfo(game);
    }

    public void playLanChessGame(int port) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Player Name: ");
        String username = scanner.nextLine();
        Client client = new Client(port, username);
        client.sendMessage("");
        String playersName = client.receiveMessage();
        System.out.println("Players are " + playersName);
        String[] names = playersName.split(" ");

        Player p1 = new UserTemp(names[0]);
        Player p2 = new UserTemp(names[1]);
        ChessGame game = new ChessGame(new Player[]{p1, p2});

        game.verbose = true;
        String moveF = "";
        String moveT = "";
        Coord moveC;
        if (names[0].equals(username)) {
            while(true){ //change it to game.gameOver() later when it is implemented.
                sendMoves(client, game);
                System.out.println("Waiting For " + names[1] + " to make a move");
                receiveMoves(client, game);
            }
        }
        else{
            while(true) {
                System.out.println("Waiting For " + names[0] + " to make a move");
                receiveMoves(client, game);
                sendMoves(client, game);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        LanChessGame lanChessGame = new LanChessGame();
        lanChessGame.playLanChessGame(1234);
    }
}
