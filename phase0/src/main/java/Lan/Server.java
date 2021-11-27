package Lan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

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
            System.out.println(clientHandler.clientUsername);
            clientHandlers.add(clientHandler);
        }
        for (ClientHandler client : clientHandlers) {
            playerNames += " " + client.clientUsername;
        }
        broadcastMessage(playerNames.strip());
    }

    //Closes Server
    public void closeServer() {
        try {
            if (this.serverSocket != null){
                this.serverSocket.close();
            }
        }catch (IOException e){


        }
    }
    //Sends Message to all clients in the Server
    public void broadcastMessage(String message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }

    //testing Main
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer(2, 1234);
        server.closeServer();
    }

}

