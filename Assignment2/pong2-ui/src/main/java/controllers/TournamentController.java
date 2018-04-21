package controllers;

import dtos.MatchDTO;
import dtos.TournamentDTO;
import dtos.UserDTO;
import entities.Tournament;
import entities.User;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import util.UserSession;

import java.net.URL;
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

        for(TournamentDTO t : tournaments.get()) {
            t.getEnroll().setVisible(Float.parseFloat(currentUser.getBalance()) >= t.getFee());
            t.getEnroll().setOnAction(new EnrollmentHandler(t.getFee()));
        }

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

    }

    private class EnrollmentHandler implements EventHandler<ActionEvent> {

        private float fee;
        public EnrollmentHandler(float fee) {
            this.fee = fee;
        }

        @Override
        public void handle(ActionEvent event) {
            float newBalance = Float.parseFloat(currentUser.getBalance()) - fee;
            currentUser.setBalance("" + newBalance);

            // update database
            User newUser = currentUser.toUser();
        }
    }
}
