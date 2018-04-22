package controllers;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import util.RuntimeUtil;
import util.UserSession;

import java.io.IOException;
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

    @FXML // fx:id="connectionMode"
    private ComboBox<String> connectionMode; // Value injected by FXMLLoader

    @FXML
    void checkCredentials(ActionEvent event) {
        User user = RuntimeUtil.logIn(userField.getText(), passField.getText());

        if(user != null) {
            status.setValue("Welcome, " + user.getName());
            try {
                Parent root = FXMLLoader.load(getClass().getResource("../starters/tournamentlist.fxml"));
                UserSession.getStage().setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
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
        connectionMode.getItems().addAll("Hibernate", "DAO");
        connectionMode.getSelectionModel().select(UserSession.getFactoryString());
        connectionMode.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("Changed Connection to " + connectionMode.getSelectionModel().getSelectedItem());
                UserSession.setFactory(connectionMode.getSelectionModel().getSelectedItem());
            }
        });
    }
}