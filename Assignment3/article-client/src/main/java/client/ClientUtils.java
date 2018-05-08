package client;

import javafx.collections.ObservableList;
import model.ArticleDTO;

import java.io.*;
import java.net.Socket;

public class ClientUtils {

    private static Socket serverCon = null;
    private static ObservableList<ArticleDTO> articles = null;
    private static ArticleDTO currentArticle = null;
    private static ObjectOutput socketOut = null;
    private static ObjectInput socketIn = null;

    private ClientUtils() {

    }

    public static Socket getServerCon() {
        return serverCon;
    }

    public static void setServerCon(Socket serverCon) {
        try {
            ClientUtils.socketOut = new ObjectOutputStream(serverCon.getOutputStream());    // see ObjectInputStream docs
            ClientUtils.socketIn = new ObjectInputStream(serverCon.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientUtils.serverCon = serverCon;
    }

    public static ObservableList<ArticleDTO> getArticles() {
        return articles;
    }

    public static void addArticle(ArticleDTO a) {
        articles.add(a);
    }

    public static void setArticles(ObservableList<ArticleDTO> articles) {
        ClientUtils.articles = articles;
    }


    public static ArticleDTO getCurrentArticle() {
        return currentArticle;
    }

    public static void setCurrentArticle(ArticleDTO currentArticle) {
        ClientUtils.currentArticle = currentArticle;
    }

    public static ObjectOutput getSocketOut() {
        return socketOut;
    }

    public static ObjectInput getSocketIn() {
        return socketIn;
    }
}
