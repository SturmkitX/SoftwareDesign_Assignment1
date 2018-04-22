package controllers;

import dtos.UserDTO;
import entities.Match;
import entities.Tournament;
import entities.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import util.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersController implements Initializable {

    private ListProperty<UserDTO> userDTOS = new SimpleListProperty<>();

    @FXML // fx:id="userTable"
    private TableView<UserDTO> userTable; // Value injected by FXMLLoader

    @FXML // fx:id="idCol"
    private TableColumn<UserDTO, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="nameCol"
    private TableColumn<UserDTO, String> nameCol; // Value injected by FXMLLoader

    @FXML // fx:id="emailCol"
    private TableColumn<UserDTO, String> emailCol; // Value injected by FXMLLoader

    @FXML // fx:id="passCol"
    private TableColumn<UserDTO, String> passCol; // Value injected by FXMLLoader

    @FXML // fx:id="adminCol"
    private TableColumn<UserDTO, ComboBox<String>> adminCol; // Value injected by FXMLLoader

    @FXML // fx:id="balanceCol"
    private TableColumn<UserDTO, Float> balanceCol; // Value injected by FXMLLoader

    @FXML // fx:id="balanceField"
    private Text balanceField; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private Text usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="nameAddField"
    private TextField nameAddField; // Value injected by FXMLLoader

    @FXML // fx:id="emailAddField"
    private TextField emailAddField; // Value injected by FXMLLoader

    @FXML // fx:id="passAddField"
    private TextField passAddField; // Value injected by FXMLLoader

    @FXML // fx:id="adminAddField"
    private ComboBox<Boolean> adminAddField; // Value injected by FXMLLoader

    @FXML // fx:id="balanceAddField"
    private TextField balanceAddField; // Value injected by FXMLLoader

    @FXML // fx:id="userAddField"
    private Button userAddField; // Value injected by FXMLLoader

    @FXML // fx:id="idAddField"
    private TextField idAddField; // Value injected by FXMLLoader

    @FXML // fx:id="emailStatus"
    private Text emailStatus; // Value injected by FXMLLoader

    @FXML // fx:id="tournamentView"
    private Button tournamentView; // Value injected by FXMLLoader

    @FXML // fx:id="connectionMode"
    private ComboBox<String> connectionMode; // Value injected by FXMLLoader

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        usernameField.setText(UserSession.getLogged().getName());
        balanceField.setText("" + UserSession.getLogged().getBalance());

        Set<User> users = UserSession.getFactory().getUserDatabase().findAll();
        userDTOS.set(getDTOSFromSet(users));

        userTable.setItems(userDTOS);
        idCol.setCellValueFactory(new PropertyValueFactory<UserDTO, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("email"));
        passCol.setCellValueFactory(new PropertyValueFactory<UserDTO, String>("password"));
        adminCol.setCellValueFactory(new PropertyValueFactory<UserDTO, ComboBox<String>>("admin"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<UserDTO, Float>("balance"));

        adminAddField.getItems().addAll(false, true);

        userTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserDTO>() {
            @Override
            public void changed(ObservableValue<? extends UserDTO> observable, UserDTO oldValue, UserDTO newValue) {
                System.out.println(newValue.balanceProperty().get());
                nameAddField.setText(newValue.nameProperty().getValue());
                passAddField.setText(newValue.getPassword());
                emailAddField.setText(newValue.getEmail());
                balanceAddField.setText(newValue.getBalance());
                adminAddField.selectionModelProperty().get().select(newValue.getAdmin().getSelectionModel().getSelectedItem());
                idAddField.setText(newValue.getId() + "");
            }
        });

        userAddField.setOnAction(new UserAddHandler());
        tournamentView.setOnAction(new TournamentViewHandler());

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

    private ObservableList<UserDTO> getDTOSFromSet(Set<User> users) {
        ObservableList<UserDTO> result = FXCollections.observableArrayList();
        for(User u : users) {
            result.add(new UserDTO(u));
        }
        return result;
    }

    private class UserAddHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            UserDTO selected = userTable.getSelectionModel().getSelectedItem();
            Set<Match> m1;
            Set<Match> m2;
            Set<Tournament> t;
            User user;

            // check if email is valid
            boolean correctEmail = Pattern.compile("\\S+@\\S+\\.\\S+").matcher(emailAddField.getText()).find();
            if(!correctEmail) {
                emailStatus.setText("Incorrect e-mail format!");
                return;
            }

            if(idAddField.getText().isEmpty()) {
                // insert user
                m1 = new HashSet<>();
                m2 = new HashSet<>();
                t = new HashSet<>();
                user = new User(0, emailAddField.getText(), passAddField.getText(), nameAddField.getText(),
                        adminAddField.getSelectionModel().getSelectedItem(), Float.parseFloat(balanceAddField.getText()),
                        m1, m2, t);
                UserSession.getFactory().getUserDatabase().insertUser(user);
                userDTOS.add(new UserDTO(user));
            }
            else {
                // update user
                m1 = selected.getSource().getMatchesP1();
                m2 = selected.getSource().getMatchesP2();
                t = selected.getSource().getTournaments();
                user = new User(selected.getId(), emailAddField.getText(), passAddField.getText(), nameAddField.getText(),
                        adminAddField.getSelectionModel().getSelectedItem(), Float.parseFloat(balanceAddField.getText()),
                        m1, m2, t);
                UserSession.getFactory().getUserDatabase().updateUser(user);
                userDTOS.remove(selected);
                userDTOS.add(new UserDTO(user));
            }

        }
    }

    private class TournamentViewHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../starters/tournamentlist.fxml"));
                UserSession.getStage().setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
