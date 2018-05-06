package server;

import article.ArticleJson;
import article.ArticleUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.UserDAO;
import requests.Request;
import user.User;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static final int BUFFER_SIZE = 1024;

    private Socket client;
    private boolean active;
    private InputStream in;
    private OutputStream out;
    byte[] buffer;
    ObjectMapper mapper;

    public ClientHandler(Socket client) {
        this.client = client;
        this.active = true;
        this.buffer = new byte[BUFFER_SIZE];
        try {
            this.in = new BufferedInputStream(client.getInputStream());
            this.out = new BufferedOutputStream(client.getOutputStream());
            this.mapper = new ObjectMapper();
            this.mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
            this.mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    @Override
//    public void run() {
//        FileOutputStream fileOut = null;
//        try {
//            fileOut = new FileOutputStream("server-json/" + client.getInetAddress().getHostAddress());
//            while(active) {
//                int size = in.read(buffer);
//                fileOut.write(buffer);
//                if(size < BUFFER_SIZE) {
//                    processData("server-json/" + client.getInetAddress().getHostAddress());
//                    fileOut.getChannel().truncate(0);
//                }
//            }
//            fileOut.close();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void run() {

        try {
            while(active) {
                Request req = mapper.readValue(in, Request.class);
                req.setContent(mapper.convertValue(req.getContent(), User.class));
                System.out.println(req.getContent().getClass());
                switch(req.getRequest()) {
                    case "LOG_IN_USER" : processLogIn((User)req.getContent()); break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server disconnecting from client");

    }

    private void processData(String path) throws IOException {
        InputStream in = new FileInputStream(path);
        byte[] data = new byte[in.available()];
        in.read(data);
        in.close();
        ArticleJson a = ArticleUtils.deserializeArticle(data);

        // data should now be inserted into the database table
    }

    private void processLogIn(User toCheck) {
        User result = UserDAO.findUserAuthentication(toCheck.getEmail(), toCheck.getPassword());
        Request req = new Request();
        req.setRequest("LOG_IN_RESPONSE");
        req.setContent(result);

        try {
            mapper.writeValue(out, req);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
