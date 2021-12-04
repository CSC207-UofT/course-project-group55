package lan;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;


    public Client(int port, String username) throws IOException {
        this.socket = new Socket("localhost", port);;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.username = username;
    }

    public void sendMessage(String move) throws IOException {
        bufferedWriter.write(this.username + " " + move);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    //Used to End Close Server
    public void sendGameOver() throws IOException {
        bufferedWriter.write("GameOver");
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    public String receiveMessage() throws IOException {
        return bufferedReader.readLine();
    }

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



