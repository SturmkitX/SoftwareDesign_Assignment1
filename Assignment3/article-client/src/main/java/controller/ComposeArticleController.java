package controller;

import article.ArticleBodyData;
import article.Article;
import client.ClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ArticleDTO;
import requests.Request;
import user.User;

import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ComposeArticleController implements Initializable {

    private ObjectMapper mapper;


    @FXML // fx:id="titleField"
    private TextArea titleField; // Value injected by FXMLLoader

    @FXML // fx:id="abstractField"
    private TextArea abstractField; // Value injected by FXMLLoader

    @FXML // fx:id="bodyField"
    private TextArea bodyField; // Value injected by FXMLLoader

    @FXML // fx:id="submitBtn"
    private Button submitBtn; // Value injected by FXMLLoader

    @FXML // fx:id="addImageBtn"
    private Button addImageBtn; // Value injected by FXMLLoader

    @FXML
    private TableView<ArticleDTO> relatedTable;

    @FXML
    private TableColumn<ArticleDTO, String> titleCol;

    @FXML
    private TableColumn<ArticleDTO, String> abstractCol;

    @FXML
    private TableColumn<ArticleDTO, CheckBox> checkedCol;

    @FXML
    void insertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) addImageBtn.getScene().getWindow();
        fileChooser.setTitle("Insert Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        File file = fileChooser.showOpenDialog(stage);

        if(file == null) {
            return;
        }

        String imgString = String.format("\n<img %s>\n", file.getAbsolutePath());
        bodyField.appendText(imgString);
    }

    @FXML
    void submitArticle(ActionEvent event) {
        try {
            Article a = computeArticle();

            Request req = new Request();
            req.setRequest("SUBMIT_ARTICLE_USER");
            req.setContent(a);

            ClientUtils.getSocketOut().writeObject(mapper.writeValueAsString(req));
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapper = new ObjectMapper();

        relatedTable.itemsProperty().bind(ClientUtils.articlesProperty());
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        abstractCol.setCellValueFactory(new PropertyValueFactory<>("articleAbstract"));
        checkedCol.setCellValueFactory(new PropertyValueFactory<>("checked"));
    }

    private Article computeArticle() throws IOException {
        String[] phrases = bodyField.getText().split("\n");
        Article result = new Article();

        // the lists are already set
        User presUser = new User();
        presUser.setId(ClientUtils.getCurrentUser().getId());
        presUser.setName(ClientUtils.getCurrentUser().getName());

        result.setAuthor(presUser);
        result.setId(String.format("%d-%d", ClientUtils.getCurrentUser().getId(), System.currentTimeMillis()));
        result.setTitle(titleField.getText());
        result.setArticleAbstract(abstractField.getText());

        for(String phrase : phrases) {
            ArticleBodyData abd = new ArticleBodyData();
            if(phrase.length() > 5 && phrase.substring(0, 5).equals("<img ")) {
                File file = new File(phrase.substring(5, phrase.length() - 1));
                FileInputStream fin = new FileInputStream(file);
                byte[] buffer = new byte[fin.available()];
                fin.read(buffer);
                abd.setText(false);
                abd.setContent(Base64.getEncoder().encodeToString(buffer));
                abd.setExtension(phrase.substring(phrase.lastIndexOf(".") + 1, phrase.length() - 1));
            } else {
                abd.setText(true);
                abd.setContent(phrase);
            }
            result.getBody().add(abd);
        }

        List<String> related = relatedTable.getItems().stream().filter(a -> a.getChecked().isSelected()).map(a -> a.getId()).collect(Collectors.toList());
        result.setRelated(related);

        return result;
    }
}
