package controller;

import article.Article;
import client.ClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ArticleDTO;
import requests.Request;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ArticleDemoController implements Initializable {

    private Socket socket;
    private ObjectMapper mapper;
    private ObservableList<ArticleDTO> articleDTOS;
    private ObjectInput in;
    private ObjectOutput out;

    @FXML // fx:id="articleTable"
    private TableView<ArticleDTO> articleTable; // Value injected by FXMLLoader

    @FXML // fx:id="titleCol"
    private TableColumn<ArticleDTO, String> titleCol; // Value injected by FXMLLoader

    @FXML // fx:id="abstractCol"
    private TableColumn<ArticleDTO, String> abstractCol; // Value injected by FXMLLoader
    // private InputStream inputStream;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localhost", 5678);
            ClientUtils.setServerCon(socket);
            out = ClientUtils.getSocketOut();
            in = ClientUtils.getSocketIn();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mapper = new ObjectMapper();

        Request req = new Request();
        req.setRequest("GET_ARTICLES_METADATA");
        try {
            out.writeObject(mapper.writeValueAsString(req));

            req = mapper.readValue((String)in.readObject(), Request.class);
            System.out.println(req.getContent());
            List<Object> articles = mapper.convertValue(req.getContent(), ArrayList.class);
            System.out.println(articles);
            ClientUtils.setArticles(FXCollections.observableArrayList());

            for(Object o : articles) {
                Article a = mapper.convertValue(o, Article.class);
                ClientUtils.getArticles().add(new ArticleDTO(a));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(socket.isClosed());
        System.out.println(socket.isInputShutdown());
        System.out.println(socket.isOutputShutdown());

        articleDTOS = ClientUtils.getArticles();
        articleTable.setItems(articleDTOS);

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        abstractCol.setCellValueFactory(new PropertyValueFactory<>("articleAbstract"));

        System.out.println(socket.isClosed());
        System.out.println(socket.isInputShutdown());
        System.out.println(socket.isOutputShutdown());

        articleTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleDTO>() {
            @Override
            public void changed(ObservableValue<? extends ArticleDTO> observable, ArticleDTO oldValue, ArticleDTO newValue) {
                if(oldValue != null) {
                    oldValue.bodyProperty().set(null);
                }
                ClientUtils.setCurrentArticle(newValue);
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../view/article_detailed.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("Article details");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
