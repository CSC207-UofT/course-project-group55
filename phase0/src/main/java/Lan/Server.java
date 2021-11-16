package Lan;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//Currently works by Running Server than Client. (No multiple client allowed yet)
//Then server sends msg and client replies over and over. Can only send one message since chess is a turn based game,
//I thought having two thread running was useless.
public class Server {
    public static void main(String[] args) throws IOException {

        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        ServerSocket serverSocket = null;

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
