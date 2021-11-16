package Lan;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;



//Currently works by Running Server than Client. (No multiple client allowed yet)
//Then server sends msg and client replies over and over. Can only send one message since chess is a turn based game,
//I thought having two thread running was useless.
public class Client {
    public static void main(String[] args){
        Socket socket = null;
        InputStreamReader inputStreamReader = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try{
            socket = new Socket("localhost", 1234);

            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

            bufferedReader = new BufferedReader(inputStreamReader);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            Scanner scanner = new Scanner(System.in);

            while (true){
                //Gets Input from Server
                String msgFromServer = bufferedReader.readLine();
                System.out.println("Server: " + msgFromServer);

                //Gets Sends Message
                String msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                if (msgFromServer.equalsIgnoreCase("BYE") || msgToSend.equalsIgnoreCase("BYE"))
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null){
                    socket.close();
                }
                if (inputStreamReader != null){
                    inputStreamReader.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if (bufferedReader != null){
                    bufferedReader.close();
                }
                if (bufferedWriter != null){
                    bufferedWriter.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

