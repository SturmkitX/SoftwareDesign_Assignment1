package controller;

import client.ClientUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.UserDTO;
import requests.Request;
import user.User;

import java.io.IOException;
import java.io.ObjectOutput;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminViewController implements Initializable {

    private ListProperty<UserDTO> writers;
    private ObjectOutput out;
    private ObjectMapper mapper;

    @FXML
    private TableView<UserDTO> usersView;

    @FXML
    private TableColumn<UserDTO, Integer> idCol;

    @FXML
    private TableColumn<UserDTO, String> nameCol;

    @FXML
    private TableColumn<UserDTO, String> emailCol;

    @FXML
    private TableColumn<UserDTO, String> passCol;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passField;

    @FXML
    private Text statusField;

    @FXML
    private Button addUserBtn;

    @FXML
    private Button updateUserBtn;

    @FXML
    private Button delUserBtn;

    @FXML
    void addUser(ActionEvent event) {
        if(!Pattern.compile("\\S+@\\S+\\.\\S+").matcher(emailField.getText()).find()) {
            statusField.setText("Invalid email");
            return;
        }

        if(nameField.getText().isEmpty() || passField.getText().isEmpty()) {
            statusField.setText("No name / password provided");
            return;
        }

        User u = new User();
        u.setName(nameField.getText());
        u.setEmail(emailField.getText());
        u.setPassword(passField.getText());
        u.setRole(1);

        Request req = new Request();
        req.setRequest("ADD_WRITER");
        req.setContent(u);

        try {
            out.writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void deleteUser(ActionEvent event) {
        User u = new User();
        u.setId(Integer.parseInt(idField.getText()));
        u.setName(nameField.getText());
        u.setEmail(emailField.getText());
        u.setPassword(passField.getText());
        u.setRole(1);

        Request req = new Request();
        req.setRequest("DELETE_WRITER");
        req.setContent(u);

        try {
            out.writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateUser(ActionEvent event) {
        if(!Pattern.compile("\\S+@\\S+\\.\\S+").matcher(emailField.getText()).find()) {
            statusField.setText("Invalid email");
            return;
        }

        if(nameField.getText().isEmpty() || passField.getText().isEmpty()) {
            statusField.setText("No name / password provided");
            return;
        }

        User u = new User();
        u.setId(Integer.parseInt(idField.getText()));
        u.setName(nameField.getText());
        u.setEmail(emailField.getText());
        u.setPassword(passField.getText());
        u.setRole(1);

        Request req = new Request();
        req.setRequest("UPDATE_WRITER");
        req.setContent(u);

        try {
            out.writeObject(mapper.writeValueAsString(req));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        out = ClientUtils.getSocketOut();
        mapper = new ObjectMapper();

        writers = new SimpleListProperty<>();
        writers.bind(ClientUtils.writersProperty());

        usersView.setItems(writers);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passCol.setCellValueFactory(new PropertyValueFactory<>("password"));

        usersView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserDTO>() {
            @Override
            public void changed(ObservableValue<? extends UserDTO> observable, UserDTO oldValue, UserDTO newValue) {
                if(newValue == null) {
                    // usually happens when updating / deleting an element, the way it is done
                    return;
                }
                idField.setText("" + newValue.getId());
                nameField.setText(newValue.getName());
                emailField.setText(newValue.getEmail());
                passField.setText(newValue.getPassword());
            }
        });
    }
}
