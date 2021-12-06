package lan;

import org.junit.Test;
import org.junit.runner.notification.RunListener;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ClientTest {

    @Test
    public void testSendReceiveMessage() throws IOException {
        serverSendMessage();
        Client client1 = new Client(1234, "player1");
        Client client2 = new Client(1234, "player2");
        client1.sendMessage("");
        client2.sendMessage("");
        client1.sendMessage("I Won");
        assertEquals("player1 I Won", client2.receiveMessage());
    }

    private void serverSendMessage(){
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
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
