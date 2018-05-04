package client;

import java.net.Socket;

public class ClientUtils {

    private static Socket serverCon = null;

    private ClientUtils() {

    }

    public static Socket getServerCon() {
        return serverCon;
    }

    public static void setServerCon(Socket serverCon) {
        ClientUtils.serverCon = serverCon;
    }
}
