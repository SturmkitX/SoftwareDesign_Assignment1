package scenes.layouts;

import java.util.ArrayList;
import java.util.List;

import database.TournamentAccess;
import handlers.TournamentListHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Tournament;
import session.UserSession;
import starter.MainScreen;

public class TournamentPane extends GridPane {
	
	// the tournaments select should be automatically done
	// insert should be present on an empty spot
	// update and delete after selecting a tournament
	// all of these are possible only if the logged in used is an admin
	
	private List<Tournament> tournaments;
	private Button addTournamentBtn;
	private List<Text> tournamentTexts;
	private int rows, cols;
	
	public TournamentPane() {
		super();
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		MainScreen.getStage().setTitle("Tournament list");
		
		// show at most 3 tournaments per row
		setUpTournamentView();
		TournamentListHandler tlh = new TournamentListHandler();
		
		addTournamentBtn.setOnAction(getButtonEventHandler());
		for(Text t : tournamentTexts) {
			t.setOnMousePressed(tlh);
		}
		
		
	}
	
	private void setUpTournamentView() {
		tournaments = TournamentAccess.getAllTournaments();
		tournamentTexts = new ArrayList<Text>();
		rows = cols = 0;
		
		for(Tournament t : tournaments) {
			Text tf = new Text(t.getName());
			tournamentTexts.add(tf);
			
			add(tf, cols, rows);
			cols++;
			if(cols == 3) {
				rows++;
				cols = 0;
			}
		}
		
		addTournamentBtn = new Button("Add Tournament");
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			add(addTournamentBtn, cols, rows);
		}
		
	}
	
	private EventHandler<ActionEvent> getButtonEventHandler() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				Stage stage = new Stage();
				stage.show();
			}
			
		};
	}
	
}
