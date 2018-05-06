package server;

import article.Article;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.ArticleDAO;
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

    @Override
    public void run() {

        try {
            while(active) {
                Request req = mapper.readValue(in, Request.class);
                System.out.println(req.getContent().getClass());
                switch(req.getRequest()) {
                    case "LOG_IN_USER" : processLogIn(req.getContent()); break;
                    case "SUBMIT_ARTICLE_USER" : processSaveArticle(req.getContent()); break;
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        System.out.println("Server disconnecting from client");

    }

    private void processLogIn(Object data) {
        User toCheck = mapper.convertValue(data, User.class);
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

    private void processSaveArticle(Object data) {
        Article a = mapper.convertValue(data, Article.class);
        try {
            File file = new File("server-json/" + a.getId());
            OutputStream out = new FileOutputStream(file);
            mapper.writeValue(out, a);
            out.close();

            ArticleDAO.insertArticle(a, file.getAbsolutePath());


            Request req = new Request();
            req.setRequest("SUBMIT_ARTICLE_RESPONSE");
            req.setContent(new String("OK"));
            mapper.writeValue(this.out, req);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
