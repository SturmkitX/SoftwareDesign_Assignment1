package controller;

import article.Article;
import client.ClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ArticleDTO;
import requests.Request;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.ResourceBundle;

public class ArticleDetailController implements Initializable {

    private ArticleDTO article;
    private ObjectInput in;
    private ObjectOutput out;
    private ObjectMapper mapper;

    @FXML // fx:id="contentArea"
    private VBox contentArea; // Value injected by FXMLLoader

    @FXML // fx:id="auxLabel"
    private Label auxLabel; // Value injected by FXMLLoader

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        article = ClientUtils.getCurrentArticle();
        out = ClientUtils.getSocketOut();
        in = ClientUtils.getSocketIn();
        mapper = new ObjectMapper();

        Text title = new Text(article.getTitle());
        title.setFont(Font.font(36));
        title.setUnderline(true);

        Text articleAbstract = new Text(article.getArticleAbstract());
        articleAbstract.setFont(Font.font(24));
        Text author = new Text(article.getAuthor());

        System.out.println(article);
        System.out.println(title.getText());
        System.out.println(articleAbstract.getText());
        System.out.println(author.getText());

        // get the complete instantiation of the selected article
        // all fields are the same, except the body, which is not null
        Request req = new Request();
        req.setRequest("GET_ARTICLE_COMPLETE");
        req.setContent(article.getId());
        try {
            out.writeObject(mapper.writeValueAsString(req));
            req = mapper.readValue((String)in.readObject(), Request.class);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        Article a = mapper.convertValue(req.getContent(), Article.class);
        article.computeObservableBody(a.getBody());


        contentArea.getChildren().addAll(title, articleAbstract, author);
        contentArea.getChildren().addAll(article.getBody());
    }
}
