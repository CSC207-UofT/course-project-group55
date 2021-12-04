package lan;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    protected String clientUsername;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.clientUsername = bufferedReader.readLine().trim();
    }

    public void sendMessage(String message) throws IOException {
        this.bufferedWriter.write(message);
        this.bufferedWriter.newLine();
        //sends msg
        this.bufferedWriter.flush();
    }

    public String receiveMessage() throws IOException {
        return this.bufferedReader.readLine();
    }
}
