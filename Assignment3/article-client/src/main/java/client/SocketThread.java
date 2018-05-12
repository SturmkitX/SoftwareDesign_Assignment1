package client;

import article.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleDTO;
import model.UserDTO;
import requests.Request;
import user.User;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SocketThread implements Runnable {

    private Socket socket;
    private ObjectInput in;
    private ObjectOutput out;
    private ObjectMapper mapper;
    private boolean active;

    public SocketThread() {
        this.socket = ClientUtils.getServerCon();
        this.in = ClientUtils.getSocketIn();
        this.out = ClientUtils.getSocketOut();
        this.mapper = new ObjectMapper();
        this.active = true;
    }

    @Override
    public void run() {
        while(active) {
            try {
                Request req = mapper.readValue((String)in.readObject(), Request.class);
                switch(req.getRequest()) {
                    case "LOG_IN_RESPONSE" : processLogin(req.getContent()); break;
                    case "GET_ARTICLES_RESPONSE" : processMetadataComplete(req.getContent()); break;
                    case "CLIENT_DISCONNECT_RESPONSE" : processDisconnect(); break;
                    case "SUBMIT_ARTICLE_RESPONSE" : processArticleBroadcast(req.getContent()); break;
                    case "GET_WRITERS_LIST_RESPONSE" : processWritersList(req.getContent()); break;
                    case "ADD_WRITER_RESPONSE" : processWriterAdd(req.getContent()); break;
                    case "UPDATE_WRITER_RESPONSE" : processWriterUpdate(req.getContent()); break;
                    case "DELETE_WRITER_RESPONSE" : processWriterDelete(req.getContent()); break;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void processLogin(Object o) {
        User user = mapper.convertValue(o, User.class);
        if(user != null) {
            ClientUtils.setCurrentUser(user);
            ClientUtils.setUserRole(user.getRole());

            if(user.getRole() == 2) {
                // get the list of writers
                Request req = new Request();
                req.setRequest("GET_WRITERS_LIST");

                try {
                    out.writeObject(mapper.writeValueAsString(req));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            ClientUtils.setUserRole(-1);   // signal an error
        }
    }

    private void processMetadataComplete(Object data) {
        List articles = mapper.convertValue(data, ArrayList.class);
        ObservableList<ArticleDTO> result = FXCollections.observableArrayList();
        for(Object o : articles) {
            Article a = mapper.convertValue(o, Article.class);
            result.add(new ArticleDTO(a));
        }

        ClientUtils.setArticles(result);
    }

    private void processDisconnect() {
        try {
            out.close();
            in.close();
            socket.close();
            ClientUtils.setServerCon(null);
            ClientUtils.setArticles(FXCollections.observableArrayList());
            active = false;
            ClientUtils.setConnectedId(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processArticleBroadcast(Object o) {
        Article a = mapper.convertValue(o, Article.class);
        ClientUtils.addArticle(new ArticleDTO(a));
    }

    private void processWritersList(Object o) {
        List aux = mapper.convertValue(o, ArrayList.class);
        ObservableList<UserDTO> result = FXCollections.observableArrayList();
        for(Object x : aux) {
            User u = mapper.convertValue(x, User.class);
            result.add(new UserDTO(u));
        }

        ClientUtils.setWriters(result);
    }

    private void processWriterAdd(Object o) {
        User u = mapper.convertValue(o, User.class);
        if(u.getId() > 0) {
            ClientUtils.writersProperty().add(new UserDTO(u));
        } else {
            System.out.println("Error creating writer account");
        }
    }

    private void processWriterUpdate(Object o) {
        User u = mapper.convertValue(o, User.class);
        if(u.getId() > 0) {
            ObservableList<UserDTO> aux = FXCollections.observableArrayList(
                    ClientUtils.getWriters().stream().filter(w -> w.getSource().getId() != u.getId()).collect(Collectors.toList()));
            aux.add(new UserDTO(u));
            ClientUtils.setWriters(aux);
        } else {
            System.out.println("Error updating writer account");
        }
    }

    private void processWriterDelete(Object o) {
        User u = mapper.convertValue(o, User.class);
        if(u.getId() > 0) {
            ObservableList<UserDTO> aux = FXCollections.observableArrayList(
                    ClientUtils.getWriters().stream().filter(w -> w.getSource().getId() != u.getId()).collect(Collectors.toList()));
            ClientUtils.setWriters(aux);
        } else {
            System.out.println("Error deleting writer account");
        }
    }
}
