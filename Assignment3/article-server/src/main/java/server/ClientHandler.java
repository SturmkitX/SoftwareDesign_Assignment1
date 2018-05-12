package server;

import article.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import database.ArticleDAO;
import database.UserDAO;
import requests.Request;
import user.User;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket client;
    private boolean active;
    private ObjectInput in;
    private ObjectOutput out;
    private ObjectMapper mapper;

    public ClientHandler(Socket client) {
        this.client = client;
        this.active = true;
        try {
            this.out = new ObjectOutputStream(client.getOutputStream());
            this.in = new ObjectInputStream(client.getInputStream());
            this.mapper = new ObjectMapper();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {

        try {
            while(active) {
                Request req = mapper.readValue((String)in.readObject(), Request.class);
//                System.out.println(req.getContent().getClass());
                switch(req.getRequest()) {
                    case "LOG_IN_USER" : processLogIn(req.getContent()); break;
                    case "SUBMIT_ARTICLE_USER" : processSaveArticle(req.getContent()); break;
                    case "GET_ARTICLES" : processMetadataComplete(); break;                            // get a list of articles
                    case "CLIENT_DISCONNECT" : processDisconnect(); break;
                    case "GET_WRITERS_LIST" : processWriters(); break;
                    case "ADD_WRITER" : processWriterAdd(req.getContent()); break;
                }
            }
        } catch(IOException | ClassNotFoundException e) {
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
            out.writeObject(mapper.writeValueAsString(req));
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
            req.setContent(a);

            // perform the broadcast
            for(ClientHandler handler : ServerUtils.getClients()) {
                handler.out.writeObject(mapper.writeValueAsString(req));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processMetadataComplete() {
        List<Article> a = ArticleDAO.getAllArticlesMetadata();

        for(Article x : a) {
            String path = ArticleDAO.getArticleBodyPath(x.getId());
            try {
                InputStream is = new FileInputStream(path);
                Article ar = mapper.readValue(is, Article.class);
                x.setBody(ar.getBody());
                x.setRelated(ar.getRelated());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Request req = new Request();
        req.setRequest("GET_ARTICLES_RESPONSE");
        req.setContent(a);

        try {
            out.writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processDisconnect() {
        Request req = new Request();
        req.setRequest("CLIENT_DISCONNECT_RESPONSE");
        try {
            out.writeObject(mapper.writeValueAsString(req));

            out.close();
            in.close();
            client.close();
            active = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processWriters() {
        List<User> result = UserDAO.findAllWiters();

        Request req = new Request();
        req.setRequest("GET_WRITERS_LIST_RESPONSE");
        req.setContent(result);

        try {
            out.writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processWriterAdd(Object o) {
        User u = mapper.convertValue(o, User.class);
        UserDAO.insertWriter(u);

        Request req = new Request();
        req.setContent("ADD_WRITER_RESPONSE");
        req.setContent(u);

        try {
            out.writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
