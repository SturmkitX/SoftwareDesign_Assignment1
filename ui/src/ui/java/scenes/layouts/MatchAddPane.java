package scenes.layouts;

import java.util.ArrayList;
import java.util.List;

import database.MatchAccess;
import database.UserAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Game;
import models.Match;
import models.User;
import session.UserSession;
import starter.MainScreen;

public class MatchAddPane extends GridPane {
	
	private TextField p1Field;
	private TextField p2Field;
	private Text stageField;
	
	public MatchAddPane(int type) {
		super();
		setVgap(20);
		setHgap(20);
		
		Label p1Label = new Label("Player 1 ID: ");
		p1Field = new TextField();
		
		Label p2Label = new Label("Player 2 ID: ");
		p2Field = new TextField();
		
		Label stageLabel = new Label("Stage: ");
		stageField = new Text("" + type);
		
		Button sendBtn = new Button("Add match");
		
		add(p1Label, 0, 0);
		add(p1Field, 1, 0);
		
		add(p2Label, 0, 1);
		add(p2Field, 1, 1);
		
		add(stageLabel, 0, 2);
		add(stageField, 1, 2);
		
		add(sendBtn, 1, 4);
		
		sendBtn.setOnAction(new ActionHandler());
		
	}
	
	private class ActionHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			User p1 = UserAccess.getUserById(Integer.parseInt(p1Field.getText()));
			User p2 = UserAccess.getUserById(Integer.parseInt(p2Field.getText()));
			int stage = Integer.parseInt(stageField.getText());
			List<Game> games = new ArrayList<Game>();
			
			Match match = new Match(0, p1, p2, stage, games);
			MatchAccess.insertMatch(match);
			UserSession.getActiveTournament().addMatch(match);
			
			TournamentMatchPane.getStage().close();
			MainScreen.setScene(new Scene(new TournamentMatchPane(), 1024, 768));
		}
		
	}
}
