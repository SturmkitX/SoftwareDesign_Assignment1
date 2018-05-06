package controller;

import article.ArticleBodyData;
import article.Article;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import requests.Request;
import user.User;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;

public class ComposeArticleController implements Initializable {

    private Socket socket;
    private User user;
    ObjectMapper mapper;

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
        try {
            Article a = computeArticle();

            Request req = new Request();
            req.setRequest("SUBMIT_ARTICLE_USER");
            req.setContent(a);
            mapper.writeValue(socket.getOutputStream(), req);
            mapper.writeValue(System.out, a);

            req = mapper.readValue(socket.getInputStream(), Request.class);
            String status = (String)req.getContent();

            if(status.equals("OK")) {
                System.out.println("Article successfully submitted");
            } else {
                System.out.println("Could not submit article");
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket("localhost", 5678);
            System.out.println(socket.isConnected());
            System.out.println(socket.isClosed() + "\n");
            Request req = new Request();
            req.setRequest("LOG_IN_USER");

            user = new User();
            user.setEmail("test@testus.com");
            user.setPassword("sidetest");
            req.setContent(user);

            mapper = new ObjectMapper();
            mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
            mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);

            mapper.writeValue(socket.getOutputStream(), req);

            req = mapper.readValue(socket.getInputStream(), Request.class);
            user = mapper.convertValue(req.getContent(), User.class);
            if(user == null) {
                System.out.println("Invalid credentials");
            } else {
                System.out.println("Welcome, " + user.getName());
            }
            System.out.println("Log In ID: " + user.getId());


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Article computeArticle() throws IOException {
        String[] phrases = bodyField.getText().split("\n");
        Article result = new Article();

        // the lists are already set
        User presUser = new User();
        presUser.setId(user.getId());
        presUser.setName(user.getName());

        result.setAuthor(presUser);
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
