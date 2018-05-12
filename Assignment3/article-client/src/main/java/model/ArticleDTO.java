package model;

import article.Article;
import article.ArticleBodyData;
import client.ClientUtils;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class ArticleDTO {

    private Article source;
    private StringProperty id;
    private StringProperty title;
    private StringProperty articleAbstract;
    private StringProperty author;
    private ListProperty<Node> body;
    private ListProperty<ArticleDTO> related;
    private CheckBox checked;

    public ArticleDTO(Article src) {
        this.source = src;
        this.id = new SimpleStringProperty(src.getId());
        this.title = new SimpleStringProperty(src.getTitle());
        this.articleAbstract = new SimpleStringProperty(src.getArticleAbstract());
        this.author = new SimpleStringProperty(src.getAuthor().getName());
        this.checked = new CheckBox();

        this.related = new SimpleListProperty<>();
        this.body = new SimpleListProperty<>();
    }

    public void computeObservableBody() {
        ObservableList<Node> result = FXCollections.observableArrayList();
        for(ArticleBodyData abd : source.getBody()) {
            Node n = null;
            if(abd.isText()) {
                n = new Text(abd.getContent());
            } else {
                n = new ImageView(new Image(new ByteArrayInputStream(Base64.getDecoder().decode(abd.getContent()))));
            }
            result.add(n);
        }

        this.bodyProperty().set(result);
    }

    public void computeObservableRelated() {
        ObservableList<ArticleDTO> result = FXCollections.observableArrayList();
        ObservableList<ArticleDTO> interm = ClientUtils.getArticles();

        for(String s : source.getRelated()) {
            for(ArticleDTO dto : interm) {
                if(dto.getId().equals(s)) {
                    result.add(dto);
                    break;
                }
            }
        }

        this.relatedProperty().set(result);
    }

    public Article getSource() {
        return source;
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

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public ObservableList<ArticleDTO> getRelated() {
        return related.get();
    }

    public ListProperty<ArticleDTO> relatedProperty() {
        return related;
    }

    public CheckBox getChecked() {
        return checked;
    }
}
