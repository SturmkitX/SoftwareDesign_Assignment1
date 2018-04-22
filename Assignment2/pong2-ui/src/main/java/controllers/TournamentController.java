package controllers;

import dtos.MatchDTO;
import dtos.TournamentDTO;
import dtos.UserDTO;
import entities.Game;
import entities.Match;
import entities.Tournament;
import entities.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import org.w3c.dom.events.Event;
import util.UserSession;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class TournamentController implements Initializable {

    private ListProperty<TournamentDTO> tournaments = new SimpleListProperty<>();
    private ObjectProperty<TournamentDTO> fd = new SimpleObjectProperty<>();
    private UserDTO currentUser = new UserDTO(UserSession.getLogged());

    @FXML // fx:id="tableField"
    private TableView<TournamentDTO> tableField; // Value injected by FXMLLoader

    @FXML // fx:id="idCol"
    private TableColumn<TournamentDTO, Integer> idCol; // Value injected by FXMLLoader

    @FXML // fx:id="nameCol"
    private TableColumn<TournamentDTO, String> nameCol; // Value injected by FXMLLoader

    @FXML // fx:id="feeCol"
    private TableColumn<TournamentDTO, Float> feeCol; // Value injected by FXMLLoader

    @FXML // fx:id="statusCol"
    private TableColumn<TournamentDTO, String> statusCol; // Value injected by FXMLLoader

    @FXML // fx:id="dateCol"
    private TableColumn<TournamentDTO, String> dateCol; // Value injected by FXMLLoader

    @FXML // fx:id="matchField"
    private TableView<MatchDTO> matchField; // Value injected by FXMLLoader

    @FXML // fx:id="matchID"
    private TableColumn<MatchDTO, Integer> matchID; // Value injected by FXMLLoader

    @FXML // fx:id="matchP1"
    private TableColumn<MatchDTO, String> matchP1; // Value injected by FXMLLoader

    @FXML // fx:id="matchP2"
    private TableColumn<MatchDTO, String> matchP2; // Value injected by FXMLLoader

    @FXML // fx:id="matchStage"
    private TableColumn<MatchDTO, String> matchStage; // Value injected by FXMLLoader

    @FXML // fx:id="usernameField"
    private Text usernameField; // Value injected by FXMLLoader

    @FXML // fx:id="balanceField"
    private Text balanceField; // Value injected by FXMLLoader

    @FXML // fx:id="actionColumn"
    private TableColumn<TournamentDTO, Button> actionColumn; // Value injected by FXMLLoader

    @FXML // fx:id="addTourName"
    private TextField addTourName; // Value injected by FXMLLoader

    @FXML // fx:id="addTourFee"
    private TextField addTourFee; // Value injected by FXMLLoader

    @FXML // fx:id="addTourButton"
    private Button addTourButton; // Value injected by FXMLLoader

    @FXML // fx:id="usersView"
    private Button usersView; // Value injected by FXMLLoader

    @FXML // fx:id="connectionMode"
    private ComboBox<String> connectionMode; // Value injected by FXMLLoader

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Tournament> tours = UserSession.getFactory().getTournamentDatabase().findAll();
        tournaments.set(TournamentDTO.getTournamentLists(tours));
        tableField.setItems(tournaments);

        idCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO, Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO, String>("name"));
        feeCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO, Float>("fee"));
        statusCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO, String>("status"));
        dateCol.setCellValueFactory(new PropertyValueFactory<TournamentDTO, String>("startDate"));

        fd.bind(tableField.getSelectionModel().selectedItemProperty());
//        System.out.println(fd.get() == null);

        matchID.setCellValueFactory(new PropertyValueFactory<MatchDTO, Integer>("id"));
        matchP1.setCellValueFactory(new PropertyValueFactory<MatchDTO, String>("p1"));
        matchP2.setCellValueFactory(new PropertyValueFactory<MatchDTO, String>("p2"));
        matchStage.setCellValueFactory(new PropertyValueFactory<MatchDTO, String>("stage"));

        usernameField.textProperty().bind(currentUser.nameProperty());
        balanceField.textProperty().bind(currentUser.balanceProperty());

        actionColumn.setCellValueFactory(new PropertyValueFactory<TournamentDTO, Button>("enroll"));

        addTourName.setVisible(UserSession.getLogged().isAdmin());
        addTourFee.setVisible(UserSession.getLogged().isAdmin());
        addTourButton.setVisible(UserSession.getLogged().isAdmin());
        addTourButton.setOnAction(new AddTournamentHandler());

        usersView.setVisible(UserSession.getLogged().isAdmin());
        usersView.setOnAction(new UserViewFireHandler());

        for(TournamentDTO t : tournaments.get()) {
            t.getEnroll().setVisible(Float.parseFloat(currentUser.getBalance()) >= t.getFee() && !t.getSource().getUsers().contains(UserSession.getLogged()));
            t.getEnroll().setOnAction(new EnrollmentHandler(t.getFee()));
        }

        tournaments.addListener(new ChangeListener<ObservableList<TournamentDTO>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<TournamentDTO>> observable, ObservableList<TournamentDTO> oldValue, ObservableList<TournamentDTO> newValue) {
                for(TournamentDTO t : newValue) {
                    t.getEnroll().setVisible(UserSession.getLogged().getBalance() >= t.getFee() && !t.getSource().getUsers().contains(UserSession.getLogged()));
                }
            }
        });

        fd.addListener(new ChangeListener<TournamentDTO>() {
            @Override
            public void changed(ObservableValue<? extends TournamentDTO> observable, TournamentDTO oldValue, TournamentDTO newValue) {
                matchField.setItems(newValue.matchesProperty().getValue());
            }
        });

        currentUser.balanceProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                for(TournamentDTO t : tournaments.get()) {
                    t.getEnroll().setVisible(Float.parseFloat(currentUser.getBalance()) >= t.getFee());
                }
            }
        });

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

    private class EnrollmentHandler implements EventHandler<ActionEvent> {

        private float fee;
        EnrollmentHandler(float fee) {
            this.fee = fee;
        }

        @Override
        public void handle(ActionEvent event) {
            float newBalance = Float.parseFloat(currentUser.getBalance()) - fee;
            currentUser.setBalance("" + newBalance);
            currentUser.getSource().setBalance(newBalance);

            // update database
            User newUser = currentUser.getSource();
            Tournament tour = fd.get().getSource();
//            newUser.getTournaments().add(tour);
            tour.getUsers().add(newUser);

            // select a proper match position for the user
            boolean found = false;
            for(Match m : tour.getMatches()) {
                if(m.getP2() == null) {
                    m.setP2(newUser);
                    found = true;
                }
            }
            if(found == false) {
                Match newMatch = new Match(0, newUser, null, 0, new HashSet<Game>(), tour);
                UserSession.getFactory().getMatchDatabase().insertMatch(newMatch);
                tour.getMatches().add(newMatch);
            }

            // UserSession.getFactory().getUserDatabase().updateUser(newUser);
            UserSession.getFactory().getTournamentDatabase().updateTournament(tour);

            matchField.setItems(fd.get().getMatches());
        }
    }

    private class AddTournamentHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Tournament tour = new Tournament(0, addTourName.getText(), Float.parseFloat(addTourFee.getText()),
                    0, new HashSet<Match>(), new Date(System.currentTimeMillis()), new HashSet<User>());
            UserSession.getFactory().getTournamentDatabase().insertTournament(tour);
            tournaments.add(new TournamentDTO(tour));
        }
    }

    private class UserViewFireHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../starters/userview.fxml"));
                UserSession.getStage().setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
