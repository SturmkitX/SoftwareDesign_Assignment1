package client;

import javafx.collections.ObservableList;
import model.ArticleDTO;

import java.net.Socket;

public class ClientUtils {

    private static Socket serverCon = null;
    private static ObservableList<ArticleDTO> articles;

    private ClientUtils() {

    }

    public static Socket getServerCon() {
        return serverCon;
    }

    public static void setServerCon(Socket serverCon) {
        ClientUtils.serverCon = serverCon;
    }

    public static ObservableList<ArticleDTO> getArticles() {
        return articles;
    }

    public static void addArticle(ArticleDTO a) {
        articles.add(a);
    }
}
