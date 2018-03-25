package handlers;

import database.GameAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import models.Game;
import models.Match;
import scenes.layouts.MatchDetailPane;

public class GameAddHandler implements EventHandler<ActionEvent> {
	
	private Match match;
	
	public GameAddHandler(Match match) {
		this.match = match;
	}

	public void handle(ActionEvent arg0) {
		Game g = new Game(0, 0, 0);
		GameAccess.insertGame(g);
		
		match.getGames().add(g);
		MatchDetailHandler.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
	}

}
