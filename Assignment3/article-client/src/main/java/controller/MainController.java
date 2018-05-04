package controller;

import client.ClientUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML // fx:id="ipField"
    private TextField ipField; // Value injected by FXMLLoader

    @FXML // fx:id="portField"
    private TextField portField; // Value injected by FXMLLoader

    @FXML // fx:id="connectButton"
    private Button connectButton; // Value injected by FXMLLoader

    @FXML // fx:id="connectionStatus"
    private Text connectionStatus; // Value injected by FXMLLoader

    @FXML // fx:id="emailField"
    private TextField emailField; // Value injected by FXMLLoader

    @FXML // fx:id="passField"
    private TextField passField; // Value injected by FXMLLoader

    @FXML // fx:id="logInButton"
    private Button logInButton; // Value injected by FXMLLoader

    @FXML // fx:id="articleView"
    private ListView<?> articleView; // Value injected by FXMLLoader

    @FXML // fx:id="loggedUserName"
    private Text loggedUserName;

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
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
