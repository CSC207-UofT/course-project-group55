package lan;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//Client and Server can communicate. I will be using these to make methods that are
//needed for our chess Lan Game. For This to work, run server, then client. Then Server has to send message first
//then client, then server back and forth
public class Server {
    public static void main(String[] args) throws IOException {

        Socket socket;
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        BufferedReader bufferedReader;
        BufferedWriter bufferedWriter;
        ServerSocket serverSocket;

        serverSocket = new ServerSocket(1234);

        while (true){
            try {
                socket = serverSocket.accept();
                System.out.println("Client1 Joined");

                inputStreamReader = new InputStreamReader(socket.getInputStream());
                outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

                bufferedReader = new BufferedReader(inputStreamReader);
                bufferedWriter = new BufferedWriter(outputStreamWriter);

                Scanner scanner = new Scanner(System.in);

                //game starts
                while (true){
                    //send message
                    String msgToSend = scanner.nextLine();

                    //Make move in the board
                    bufferedWriter.write(msgToSend);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();

                    //recieve message
                    String msgFromClient = bufferedReader.readLine();
                    System.out.println("Client: " + msgFromClient);



                    if (msgFromClient.equalsIgnoreCase("BYE") || msgToSend.equalsIgnoreCase("BYE"))
                        break;
                }

                socket.close();
                inputStreamReader.close();
                outputStreamWriter.close();
                bufferedReader.close();
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
