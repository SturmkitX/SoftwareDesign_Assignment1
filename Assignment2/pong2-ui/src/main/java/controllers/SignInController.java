package controllers;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.RuntimeUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    private StringProperty status = new SimpleStringProperty("");

    @FXML // fx:id="userField"
    private TextField userField; // Value injected by FXMLLoader

    @FXML // fx:id="passField"
    private PasswordField passField; // Value injected by FXMLLoader

    @FXML // fx:id="signButton"
    private Button signButton; // Value injected by FXMLLoader

    @FXML // fx:id="logStatus"
    private Text logStatus; // Value injected by FXMLLoader

    @FXML
    void checkCredentials(ActionEvent event) {
        User user = RuntimeUtil.logIn(userField.getText(), passField.getText());

        if(user != null) {
            status.setValue("Welcome, " + user.getName());
        } else {
            status.setValue("Wrong email or password!");
        }
    }

    @FXML
    void exitApp(ActionEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logStatus.textProperty().bind(status);
    }
}