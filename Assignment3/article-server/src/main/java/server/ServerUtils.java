package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerUtils {

    private static List<Socket> clients = Collections.synchronizedList(new ArrayList<Socket>());

    private ServerUtils() {

    }

    public static List<Socket> getClients() {
        return clients;
    }

    public static void setClients(List<Socket> clients) {
        ServerUtils.clients = clients;
    }

    public static void addClient(Socket socket) {
        ServerUtils.clients.add(socket);
    }
}
