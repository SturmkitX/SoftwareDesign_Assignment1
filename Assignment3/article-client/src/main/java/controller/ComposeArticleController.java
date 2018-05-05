package controller;

import article.ArticleBodyData;
import article.ArticleJson;
import article.ArticleUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import user.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class ComposeArticleController implements Initializable {

    private Socket socket;
    private User user;

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
    void insertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) addImageBtn.getScene().getWindow();
        fileChooser.setTitle("Insert Image");
        File file = fileChooser.showOpenDialog(stage);

        if(file == null) {
            return;
        }

        String imgString = String.format("\n<img %s>\n", file.getAbsolutePath());
        bodyField.appendText(imgString);
    }

    @FXML
    void submitArticle(ActionEvent event) {
        byte[] data = null;
        try {
            ArticleJson a = computeArticle();
            data = ArticleUtils.serializeArticle(a);
        } catch(IOException e) {
            e.printStackTrace();
        }

        if(data != null) {
            try {
                OutputStream out = socket.getOutputStream();
                out.write(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localhost", 5678);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user = new User();
        user.setId(1);
    }

    private ArticleJson computeArticle() throws IOException {
        String[] phrases = bodyField.getText().split("\n");
        ArticleJson result = new ArticleJson();

        // the lists are already set
        result.setAuthor(user.getId());
        result.setId(String.format("%d-%d", user.getId(), System.currentTimeMillis()));
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

        return result;
    }
}
