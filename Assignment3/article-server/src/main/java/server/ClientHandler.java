package server;

import article.ArticleJson;
import article.ArticleUtils;

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
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("server-json/" + client.getInetAddress().getHostAddress());
            while(active) {
                int size = in.read(buffer);
                fileOut.write(buffer);
                if(size < BUFFER_SIZE) {
                    processData("server-json/" + client.getInetAddress().getHostAddress());
                    fileOut.getChannel().truncate(0);
                }
            }
            fileOut.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void processData(String path) throws IOException {
        InputStream in = new FileInputStream(path);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        ArticleJson a = ArticleUtils.deserializeArticle(data);

        // data should now be inserted into the database table
    }
}
