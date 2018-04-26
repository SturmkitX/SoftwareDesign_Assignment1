package article;

import javafx.scene.Node;
import user.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Article {

    private int id;
    private String title;
    private String articleAbstract;
    private User author;
    private List<Node> body;
    private List<Article> related;

    public Article() {
        this.body = new LinkedList<>();
        this.related = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Node> getBody() {
        return body;
    }

    public void setBody(List<Node> body) {
        this.body = body;
    }

    public List<Article> getRelated() {
        return related;
    }

    public void setRelated(List<Article> related) {
        this.related = related;
    }
}
