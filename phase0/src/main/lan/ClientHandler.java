package lan;

import java.io.*;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    protected String clientUsername;

    /**
     * Used from Server Class to keep client's Lan-related information
     * @param socket is used to set up Sever to communicate with Server
     */
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.clientUsername = bufferedReader.readLine().trim();
    }
    /**
     * Used to send message to the connected Client
     * @param message is string message to be sent
     */
    public void sendMessage(String message) throws IOException {
        this.bufferedWriter.write(message);
        this.bufferedWriter.newLine();
        //sends msg
        this.bufferedWriter.flush();
    }

    /**
     * Used to receive message from connected Client
     * @return received message from connected Client
     */
    public String receiveMessage() throws IOException {
        return this.bufferedReader.readLine();
    }

    public String getClientUsername() {
        return this.clientUsername;
    }
}
