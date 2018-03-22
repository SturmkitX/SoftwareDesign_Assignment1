package scenes.layouts;

import java.util.ArrayList;
import java.util.List;

import database.TournamentAccess;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Tournament;

public class TournamentPane extends GridPane {
	
	// the tournaments select should be automatically done
	// insert should be present on an empty spot
	// update and delete after selecting a tournament
	// all of these are possible only if the logged in used is an admin
	
	private List<Tournament> tournaments;
	private Button addTournamentBtn;
	private List<Text> tournamentTexts;
	private int rows, cols;
	
	public TournamentPane(boolean isAdmin) {
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		// show at most 3 tournaments per row
		setUpTournamentView();
		
		
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
		add(addTournamentBtn, cols, rows);
	}
}
