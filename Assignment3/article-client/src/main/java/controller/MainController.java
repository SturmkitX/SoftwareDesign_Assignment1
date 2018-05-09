package controller;

import client.ClientUtils;
import client.SocketThread;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.ArticleDTO;
import requests.Request;
import user.User;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private IntegerProperty loggedIdProperty;
    private ObjectMapper mapper;

    @FXML
    private TextField ipField;

    @FXML
    private TextField portField;

    @FXML
    private Button connectButton;

    @FXML
    private Text connectionStatus;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passField;

    @FXML
    private Button logInButton;

    @FXML
    private Text loggedUserName;

    @FXML
    private Button signOutBtn;

    @FXML
    private TableView<ArticleDTO> articleView;

    @FXML
    private TableColumn<ArticleDTO, String> titleCol;

    @FXML
    private TableColumn<ArticleDTO, String> abstractCol;

    @FXML
    private Button adminPanelBtn;

    @FXML
    private Button disconnectBtn;

    @FXML
    private Button writeArticleBtn;

    @FXML
    void connectServer(ActionEvent event) {
        Socket socket = null;
        try {
            String serverIp = ipField.getText();
            int serverPort = Integer.parseInt(portField.getText());
            socket = new Socket(serverIp, serverPort);
        } catch(NumberFormatException e) {
            System.out.println("Port must be a number");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(socket != null) {
            ClientUtils.setServerCon(socket);
            System.out.println("Successfully connected to server");

            new Thread(new SocketThread()).start();
        }
    }

    @FXML
    void logInUser(ActionEvent event) {
        String email = emailField.getText();
        String pass = passField.getText();

        User user = new User();
        user.setEmail(email);
        user.setPassword(pass);

        Request req = new Request();
        req.setRequest("LOG_IN_USER");
        req.setContent(user);

        try {
            ClientUtils.getSocketOut().writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapper = new ObjectMapper();

        loggedIdProperty = new SimpleIntegerProperty();
        loggedIdProperty.bind(ClientUtils.userIdProperty());

        loggedIdProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > 0) {
                    System.out.println("Welcome, " + ClientUtils.getCurrentUser().getName());
                }
            }
        });
    }
}
