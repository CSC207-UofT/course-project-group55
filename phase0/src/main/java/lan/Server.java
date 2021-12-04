package lan;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket = null;
    ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    //Runs Server with numPlayers allowed at given port.
    public boolean runServer(int numPlayers, String strPort) throws IOException {
        Socket socket = null;
        int port = Integer.parseInt(strPort);
        try{
            this.serverSocket = new ServerSocket(port);
        } catch (BindException e){
            return false;
        }
        String playerNames = "";
        ClientHandler clientHandler;
        //Get numPlayer's info
        for (int i = 0; i < numPlayers; i++) {
            socket = this.serverSocket.accept();
            clientHandler = new ClientHandler(socket);
            //System.out.println(clientHandler.clientUsername);
            clientHandlers.add(clientHandler);
        }
        //can close server because all connections are made.
        closeServer();
        for (ClientHandler client : clientHandlers) {
            playerNames += " " + client.clientUsername;
        }
        messageToAll(playerNames.trim());
        int i = 0;
        String received = "";
        //loops until GameOver is sent by the Game
        //TODO: Make sure it works with
        while(!received.equals("GameOver")){
            int receiveFrom = i % numPlayers;
            received = clientHandlers.get(receiveFrom).receiveMessage();
            broadcastMessage(received);
            received = clientHandlers.get(receiveFrom).receiveMessage();
            broadcastMessage(received);
            i++;
        }
        return true;
    }

    //Closes Server
    public void closeServer() throws IOException {
        if (this.serverSocket != null){
            this.serverSocket.close();
        }
    }
    //Sends Message to all clients in the Server
    public void messageToAll(String message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }

    //sends Message to all the players other then client itself.
    public void broadcastMessage(String message) throws IOException {
        String sentby = message.split(" ")[0];
        for (ClientHandler clientHandler : clientHandlers) {
            if(!sentby.equals(clientHandler.clientUsername)){
                clientHandler.sendMessage(message);
            }
        }
    }

    //Check if the user's input is valid.
    public boolean validInput(String strPort) {
        try {
            Integer.parseInt(strPort);
        } catch (NumberFormatException e) {
            return false;
        }
        if (strPort.length() == 4) {
            return true;
        }
        return false;
    }

    //testing Main
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Four Digit Port Number");
        String strPort = scanner.nextLine();
        while(!server.validInput(strPort)){
            System.out.println("Wrong! RE-Enter Four Digit Port Number");
            strPort = scanner.nextLine();
        }
        if (!server.runServer(2, strPort)){
            System.out.println("The port is in use already");
        }
    }
}


