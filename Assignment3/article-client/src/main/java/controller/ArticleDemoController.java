package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.ArticleDTO;

import java.net.URL;
import java.util.ResourceBundle;

public class ArticleDemoController implements Initializable {

    @FXML // fx:id="articleTable"
    private TableView<ArticleDTO> articleTable; // Value injected by FXMLLoader

    @FXML // fx:id="titleCol"
    private TableColumn<ArticleDTO, String> titleCol; // Value injected by FXMLLoader

    @FXML // fx:id="abstractCol"
    private TableColumn<ArticleDTO, String> abstractCol; // Value injected by FXMLLoader

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
