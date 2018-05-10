package client;

import article.Article;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.security.ntlm.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.ArticleDTO;
import requests.Request;
import user.User;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                    case "GET_ARTICLES_METADATA_RESPONSE" : processMetadata(req.getContent()); break;
                    case "CLIENT_DISCONNECT_RESPONSE" : processDisconnect(); break;
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
        } else {
            ClientUtils.setUserRole(-1);   // signal an error
        }
    }

    private void processMetadata(Object data) {
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
}
