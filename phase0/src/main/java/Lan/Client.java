package Lan;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.username = username;
    }

    public void sendMessage(String move) throws IOException {
        bufferedWriter.write(this.username + " " + move);
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
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        //connecting to the server port 1234
        Socket socket = new Socket("localhost", 1234);
        Client client = new Client(socket, username);
        client.sendMessage("");

        String playersName = client.receiveMessage();
        System.out.println(playersName);
        client.closeSocketBuffered();
    }

}


