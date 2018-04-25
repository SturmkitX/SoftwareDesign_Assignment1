package controllers;

import dtos.GameDTO;
import entities.Game;
import entities.Match;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.RuntimeUtil;
import util.UserSession;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private ListProperty<GameDTO> games = new SimpleListProperty<>();
    private Match activeMatch;
    private Game activeGame;

    @FXML // fx:id="gameField"
    private TableView<GameDTO> gameField; // Value injected by FXMLLoader

    @FXML // fx:id="statusCol1"
    private TableColumn<GameDTO, String> statusCol1; // Value injected by FXMLLoader

    @FXML // fx:id="scoreCol1"
    private TableColumn<GameDTO, Integer> scoreCol1; // Value injected by FXMLLoader

    @FXML // fx:id="scoreCol2"
    private TableColumn<GameDTO, Integer> scoreCol2; // Value injected by FXMLLoader

    @FXML // fx:id="statusCol2"
    private TableColumn<GameDTO, String> statusCol2; // Value injected by FXMLLoader

    @FXML // fx:id="scoreField1"
    private TextField scoreField1; // Value injected by FXMLLoader

    @FXML // fx:id="scoreField2"
    private TextField scoreField2; // Value injected by FXMLLoader

    @FXML // fx:id="scoreButton"
    private Button scoreButton; // Value injected by FXMLLoader

    @FXML
    void updateScores(ActionEvent event) {
        activeGame.setP1Score(Integer.parseInt(scoreField1.getText()));
        activeGame.setP2Score(Integer.parseInt(scoreField2.getText()));
        UserSession.getFactory().getGameDatabase().updateGame(activeGame);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<GameDTO> gameList = FXCollections.observableArrayList();
        activeMatch = UserSession.getActiveMatch();
        for(Game g : activeMatch.getGames()) {
            gameList.add(new GameDTO(g));
        }

        games.set(gameList);
        gameField.setItems(games);
        gameField.itemsProperty().bind(games);

        scoreField1.setEditable(activeMatch.getP1().equals(UserSession.getLogged()));
        scoreField2.setEditable(activeMatch.getP2().equals(UserSession.getLogged()));

        statusCol1.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("p1Status"));
        scoreCol1.setCellValueFactory(new PropertyValueFactory<GameDTO, Integer>("p1Score"));
        scoreCol2.setCellValueFactory(new PropertyValueFactory<GameDTO, Integer>("p2Score"));
        statusCol2.setCellValueFactory(new PropertyValueFactory<GameDTO, String>("p2Status"));

        gameField.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<GameDTO>() {
            @Override
            public void changed(ObservableValue<? extends GameDTO> observable, GameDTO oldValue, GameDTO newValue) {
                scoreField1.setText("" + newValue.getP1Score());
                scoreField2.setText("" + newValue.getP2Score());
                activeGame = newValue.getSource();

                // check if we should add a new game
                int winner = RuntimeUtil.getWinner(activeGame);
                if(winner == 1) {
                    statusCol1.setText("Winner");
                } else if(winner == 2) {
                    statusCol2.setText("Winner");
                }

                if(winner > 0 && RuntimeUtil.getWinner(activeMatch) == 0) {
                    Game g = new Game(0, 0, 0, activeMatch);
                    activeMatch.getGames().add(g);
                    UserSession.getFactory().getGameDatabase().insertGame(g);
                    UserSession.getFactory().getMatchDatabase().updateMatch(activeMatch);
                }
            }
        });


    }
}
