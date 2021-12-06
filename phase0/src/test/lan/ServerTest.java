package lan;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerTest {

    @Test
    public void TestAddClientHandler() throws IOException {
        makeServerClientHandler();
        Client client1 = new Client(1234, "player1");
        Client client2 = new Client(1234, "player2");
        client1.sendMessage("");
        client2.sendMessage("");
        assertEquals("player1player2", client1.receiveMessage());
    }

    @Test
    public void TestBroadcastMessage() throws IOException {
        makeServerBroadcast();
        Client client1 = new Client(1234, "player1");
        Client client2 = new Client(1234, "player2");
        client1.sendMessage("");
        client2.sendMessage("");
        client1.sendMessage("e1");
        assertEquals("player1 e1", client2.receiveMessage());
        client2.sendMessage("e4");
        assertEquals("player2 e4", client1.receiveMessage());
    }

    @Test
    public void TestMessageAll() throws IOException {
        makeServerMessageAll();
        Client client1 = new Client(1234, "player1");
        Client client2 = new Client(1234, "player2");
        client1.sendMessage("");
        client2.sendMessage("");
        assertEquals("Client1 Client2", client1.receiveMessage());
        assertEquals("Client1 Client2", client2.receiveMessage());
    }

    @Test
    public void testValidInput(){
        Server server = new Server();
        assertFalse(server.validInput("123f"));
        assertFalse(server.validInput("qw~w"));
        assertFalse(server.validInput("123"));
        assertFalse(server.validInput("12323"));
        assertTrue(server.validInput("1234"));

    }


    /**
     * Helper methods for testing
     */
    private void makeServerClientHandler(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server server = new Server();
                    server.startServer(1234);
                    server.addClientHandler();
                    server.addClientHandler();
                    String clientNames = "";
                    for (int i = 0; i < 2; i++){
                        clientNames += server.clientHandlers.get(i).getClientUsername();
                    }
                    server.messageToAll(clientNames);
                    server.closeServer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void makeServerBroadcast(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Server server = new Server();
                    server.startServer(1234);
                    server.addClientHandler();
                    server.addClientHandler();
                    server.closeServer();
                    String received = server.clientHandlers.get(0).receiveMessage();
                    server.broadcastMessage(received);
                    received = server.clientHandlers.get(1).receiveMessage();
                    server.broadcastMessage(received);
                    server = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void makeServerMessageAll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server server = new Server();
                    server.startServer(1234);
                    server.addClientHandler();
                    server.addClientHandler();
                    server.closeServer();
                    server.messageToAll("Client1 Client2");
                    server = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
