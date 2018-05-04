package server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private Socket client;
    private boolean active;
    private InputStream in;
    private OutputStream out;
    byte[] buffer;

    public ClientHandler(Socket client) {
        this.client = client;
        this.active = true;
        this.buffer = new byte[BUFFER_SIZE];
        try {
            this.in = new BufferedInputStream(client.getInputStream());
            this.out = new BufferedOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        StringBuilder sb = new StringBuilder();
        try {
            while(active) {
                int size = in.read(buffer);
                sb.append(buffer);
                if(size < BUFFER_SIZE) {
                    processData(sb.toString());
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void processData(String data) {
        System.out.println(data);
    }
}
