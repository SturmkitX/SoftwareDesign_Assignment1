package controller;

import client.ClientUtils;
import client.SocketThread;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ArticleDTO;
import requests.Request;
import user.User;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private IntegerProperty loggedIdProperty;
    private IntegerProperty connectedProperty;
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
            ClientUtils.connectedIdProperty().set(-1);
        }

        if(socket != null) {
            ClientUtils.setServerCon(socket);
            ClientUtils.setConnectedId(1);
            System.out.println("Successfully connected to server");

            new Thread(new SocketThread()).start();

            generateArticles();
        }

        // set the automatic disconnection mechanism
        Stage stage = (Stage) connectButton.getScene().getWindow();
        stage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(ClientUtils.getServerCon() == null) {
                    return;
                }

                Request req = new Request();
                req.setRequest("CLIENT_DISCONNECT");
                try {
                    ClientUtils.getSocketOut().writeObject(mapper.writeValueAsString(req));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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

    @FXML
    void disconnectServer(ActionEvent event) {
        if(ClientUtils.getServerCon() == null) {
            System.out.println("You are not connected to a server");
            return;
        }

        Request req = new Request();
        req.setRequest("CLIENT_DISCONNECT");

        try {
            ClientUtils.getSocketOut().writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signOutUser(ActionEvent event) {
        if(ClientUtils.getCurrentUser() == null) {
            System.out.println("You are not logged in as a user");
            return;
        }

        ClientUtils.setUserRole(0);
        ClientUtils.setCurrentUser(null);
    }

    @FXML
    void userWriteArticle(ActionEvent event) {
        if(ClientUtils.getCurrentUser() == null) {
            System.out.println("You are not logged in");
            return;
        }

        if(ClientUtils.getCurrentUser().getRole() != 1) {
            System.out.println("You are not a writer");
            return;
        }

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/compose_article.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();

            stage.setTitle("Compose article");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapper = new ObjectMapper();

        loggedIdProperty = new SimpleIntegerProperty();
        loggedIdProperty.bind(ClientUtils.userRoleProperty());

        connectedProperty = new SimpleIntegerProperty();
        connectedProperty.bind(ClientUtils.connectedIdProperty());

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        abstractCol.setCellValueFactory(new PropertyValueFactory<>("articleAbstract"));
        articleView.itemsProperty().bind(ClientUtils.articlesProperty());


        loggedIdProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() > 0) {
                    loggedUserName.setText(ClientUtils.getCurrentUser().getName());
                }
            }
        });

        connectedProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                switch(newValue.intValue()) {
                    case 1 : connectionStatus.setText("Connected : " + ClientUtils.getServerCon().getInetAddress().getHostName()); break;
                    case 0 : connectionStatus.setText("Disconnected"); break;
                    case -1 : connectionStatus.setText("Invalid connection parameters");
                }
            }
        });

        articleView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArticleDTO>() {
            @Override
            public void changed(ObservableValue<? extends ArticleDTO> observable, ArticleDTO oldValue, ArticleDTO newValue) {
                if(newValue == null) {
                    return;
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

    private void generateArticles() {
        Request req = new Request();
        req.setRequest("GET_ARTICLES");

        try {
            ClientUtils.getSocketOut().writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
