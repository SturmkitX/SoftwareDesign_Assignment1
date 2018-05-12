package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerUtils {

    private static List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    private ServerUtils() {

    }

    public static List<ClientHandler> getClients() {
        return clients;
    }

    public static void addClient(ClientHandler handler) {
        ServerUtils.clients.add(handler);
    }
}
