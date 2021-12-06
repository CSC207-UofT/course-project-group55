package example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

import static org.junit.Assert.*;

public class SeverClientThreadTest {
    static BlockingQueue<Thread> clients = new LinkedBlockingQueue<>();
    // map: K: client V: message
    static ConcurrentHashMap<Thread, Message> map = new ConcurrentHashMap<>();

    static class Message {
        String msg;
    }

    static class Server extends Thread {
        public void run() {
            try {
                while (true) {
                    Thread singleClient = clients.take();
                    Message message = map.get(singleClient);
                    map.remove(singleClient);
                    System.out.println("Server receive: " + message.msg);

                    message.msg = "Sever value: 321";
                    LockSupport.unpark(singleClient);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class Client {
        public Message send(){
            Message message = new Message();
            message.msg = "Client value: 123";
            map.put(Thread.currentThread(), message);
            clients.offer(Thread.currentThread());
            LockSupport.park();
            System.out.println("Client receive: " + message.msg);
            return message;
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();

        Client client = new Client();
        Message response = client.send();

        // this is one unit test
        assertEquals("Sever value: 321", response.msg);
    }
}
