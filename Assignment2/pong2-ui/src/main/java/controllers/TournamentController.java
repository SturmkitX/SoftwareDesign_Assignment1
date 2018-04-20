package controllers;

import dtos.TournamentDTO;
import entities.Tournament;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import util.UserSession;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class TournamentController implements Initializable {

    private ListProperty tournaments = new SimpleListProperty();
    private ObjectProperty<TournamentDTO> fd = new SimpleObjectProperty<>();

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

        fd.bind(tableField.getFocusModel().focusedItemProperty());
        fd.addListener(new ChangeListener<TournamentDTO>() {
            @Override
            public void changed(ObservableValue<? extends TournamentDTO> observable, TournamentDTO oldValue, TournamentDTO newValue) {
                System.out.println(newValue);
            }
        });

    }
}
