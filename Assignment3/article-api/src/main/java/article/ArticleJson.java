package article;

import user.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArticleJson {

    private String id;  // user id + upload timestamp
    private String title;
    private String articleAbstract;
    private User author;
    private List<ArticleBodyData> body;
    private List<Integer> related;

    public ArticleJson() {
        this.body = new LinkedList<>();
        this.related = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setRelated(List<Integer> related) {
        this.related = related;
    }

    public User getAuthor() {
        return author;
    }

    public List<Integer> getRelated() {
        return related;
    }

    public List<ArticleBodyData> getBody() {
        return body;
    }

    public void setBody(List<ArticleBodyData> body) {
        this.body = body;
    }
}
