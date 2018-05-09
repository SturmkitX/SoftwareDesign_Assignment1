package controller;

import article.Article;
import client.ClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

        // set the related articles table
        TableView<ArticleDTO> relatedTable = new TableView<>();
        TableColumn<ArticleDTO, String> relatedTitleCol = new TableColumn<>();
        TableColumn<ArticleDTO, String> relatedAbstractCol = new TableColumn<>();

        relatedTitleCol.setText("Title");
        relatedAbstractCol.setText("Abstract");

        article.computeObservableRelated(a.getRelated());


        relatedTable.getColumns().addAll(relatedTitleCol, relatedAbstractCol);
        relatedTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        relatedAbstractCol.setCellValueFactory(new PropertyValueFactory<>("articleAbstract"));
        relatedTable.setItems(article.getRelated());

        System.out.println(article.getRelated().size());


        contentArea.getChildren().addAll(title, articleAbstract, author);
        contentArea.getChildren().addAll(article.getBody());
        contentArea.getChildren().add(relatedTable);

        relatedTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleDTO>() {
            @Override
            public void changed(ObservableValue<? extends ArticleDTO> observable, ArticleDTO oldValue, ArticleDTO newValue) {
                ClientUtils.setCurrentArticle(newValue);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../view/article_detailed.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setTitle("Article details");
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
