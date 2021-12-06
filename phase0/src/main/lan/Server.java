package lan;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {
    ServerSocket serverSocket;
    ArrayList<ClientHandler> clientHandlers;

    public Server(){
        serverSocket = null;
        clientHandlers = new ArrayList<>();
    }

    /**
     * Used to runServer for two player chessGame.
     * @param numPlayers used to accept no more than numPlayers to the server
     * @param strPort used to set up server with given strPort
     * @return false if given strPort is taken by another server.
     * @return true fi given strPort is valid.
     */
    public boolean runServer(int numPlayers, String strPort) throws IOException {
        int port = Integer.parseInt(strPort);
        if (!startServer(port)){
            return false;
        }
        String playerNames = "";
        //Get numPlayer's info
        for (int i = 0; i < numPlayers; i++) {
            addClientHandler();
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


    /**
     *
     */
    public boolean startServer(int port) throws IOException {
        try{
            this.serverSocket = new ServerSocket(port);
        } catch (BindException e){
            return false;
        }
        return true;
    }

    /**
     * When client is connects to the server, add to Client's info to ClientHandler
     */
    public void addClientHandler() throws IOException {
        Socket socket = this.serverSocket.accept();
        ClientHandler clientHandler = new ClientHandler(socket);
        clientHandlers.add(clientHandler);
    }

    /**
     * Used to close Server
     */
    public void closeServer() throws IOException {
        if (this.serverSocket != null){
            this.serverSocket.close();
        }
    }

    /**
     * Used to message to all clients that are in ClientHander
     * @param message string message to be sent to clients
     */
    public void messageToAll(String message) throws IOException {
        for (ClientHandler clientHandler : clientHandlers) {
            clientHandler.sendMessage(message);
        }
    }

    /**
     * Used to send message to Client other than the Client who sent the message.
     * @param message string message to be sent.
     */
    //sends Message to all the players other then client itself.
    public void broadcastMessage(String message) throws IOException {
        String sentby = message.split(" ")[0];
        for (ClientHandler clientHandler : clientHandlers) {
            if(!sentby.equals(clientHandler.clientUsername)){
                clientHandler.sendMessage(message);
            }
        }
    }

    /**
     * Check if user inputted string is a valid port number
     *
     * @param strPort String your inputted as port number
     * @return true if strPort is four digit
     * @return false if strPort is not four digit (ex. string, not 4-digit, contains special character etc)
     * */
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


