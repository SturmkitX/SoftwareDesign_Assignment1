package starter;

import server.ClientHandler;
import server.ServerUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStarter {

    private static final int SERVER_PORT = 5678;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(SERVER_PORT);
        while(true) {
            Socket client = server.accept();
            ServerUtils.addClient(client);

            new Thread(new ClientHandler(client)).start();
        }
    }
}
