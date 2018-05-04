package model;

import article.Article;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.List;

public class ArticleDTO {

    private Article source;
    private IntegerProperty id;
    private StringProperty title;
    private StringProperty articleAbstract;
    private StringProperty author;
    private ListProperty<Node> body;
    private ListProperty<Article> related;

    public ArticleDTO(Article src) {
        this.source = src;
        this.id = new SimpleIntegerProperty(src.getId());
        this.title = new SimpleStringProperty(src.getTitle());
        this.articleAbstract = new SimpleStringProperty(src.getArticleAbstract());
        this.author = new SimpleStringProperty(src.getAuthor().getName());
        this.body = new SimpleListProperty<>(computeObservableBody(src.getBody()));
        this.related = new SimpleListProperty<>(computeObservableRelated(src.getRelated()));
    }

    private ObservableList<Node> computeObservableBody(List<Node> nodes) {
        ObservableList<Node> result = FXCollections.observableArrayList(nodes);
        return result;
    }

    private ObservableList<Article> computeObservableRelated(List<Article> articles) {
        ObservableList<Article> result = FXCollections.observableArrayList(articles);
        return result;
    }

    public Article getSource() {
        return source;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getArticleAbstract() {
        return articleAbstract.get();
    }

    public StringProperty articleAbstractProperty() {
        return articleAbstract;
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }

    public ObservableList<Node> getBody() {
        return body.get();
    }

    public ListProperty<Node> bodyProperty() {
        return body;
    }

    public ObservableList<Article> getRelated() {
        return related.get();
    }

    public ListProperty<Article> relatedProperty() {
        return related;
    }
}
