package lan;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    /**
     * Creates Client instance initialized with port and username
     *
     * @param port used to connect to that certain port
     * @param username name of the player
     * */
    public Client(int port, String username) throws IOException {
        this.socket = new Socket("localhost", port);
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.username = username;
    }

    /**
     * send given String, move, to the server
     *
     * @param move String that is sent to the server
     * */
    public void sendMessage(String move) throws IOException {
        bufferedWriter.write(this.username + " " + move);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    /**
     * receive message sent from the Server
     *
     * @return returns receive string
     * */
    public String receiveMessage() throws IOException {
        return bufferedReader.readLine();
    }

    /**
     * closes all the Client-Lan related parameters
     * */
    public void closeSocketBuffered() {
        try{
            //closing bufferedReader and Writer will close outputStreamWriter and InputStreamReader
            if (this.bufferedReader != null){
                this.bufferedReader.close();
            }
            if (this.bufferedWriter != null){
                this.bufferedWriter.close();
            }
            //closing socket will close socket.InputStream and socket.OutputStream
            if (this.socket != null){
                this.socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
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

    //Testing main
//    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter your username for the group chat: ");
//        String username = scanner.nextLine();
//        //connecting to the server port 1234
//        Client client = new Client(1234, username);
//        client.sendMessage("");
//        String playersName = client.receiveMessage();
//        System.out.println("Players are " + playersName);
//
//        String[] names = playersName.split(" ");
//        if (names[0].equals(client.username)){
//            System.out.println("Type Message: ");
//            client.sendMessage(scanner.nextLine());
//            System.out.println(client.receiveMessage());
//        }
//        else{
//            System.out.println(client.receiveMessage());
//            client.sendMessage(scanner.nextLine());
//        }
//        client.closeSocketBuffered();
//    }

}



