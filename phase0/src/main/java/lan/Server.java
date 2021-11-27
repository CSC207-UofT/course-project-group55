package lan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    ServerSocket serverSocket = null;
    ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    //Runs Server with numPlayers allowed at given port.
    //TODO: Later work on if there already exists port number
    public void runServer(int numPlayers, int port) throws IOException {
        Socket socket = null;
        this.serverSocket = new ServerSocket(port);
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
        messageToAll(playerNames.strip());
        int i = 0;
        String received = "";
        //loops until GameOver is sent by the Game
        while(!received.equals("GameOver")){
            int receiveFrom = i % numPlayers;
            received = clientHandlers.get(receiveFrom).receiveMessage();
            broadcastMessage(received);
            received = clientHandlers.get(receiveFrom).receiveMessage();
            broadcastMessage(received);
            i++;
        }
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
            //System.out.println(clientHandler.clientUsername + "'s Turn to receive message");
            //System.out.println("The Message is sent by : " + sentby + " and received by " + clientHandler.clientUsername);
            if(!sentby.equals(clientHandler.clientUsername)){
                clientHandler.sendMessage(message);
            }
        }
    }

    //testing Main
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer(2, 1234);
    }
}


