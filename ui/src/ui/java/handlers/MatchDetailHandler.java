package handlers;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Match;
import scenes.layouts.MatchDetailPane;
import session.UserSession;

public class MatchDetailHandler implements EventHandler<MouseEvent> {
	
	private Match match;
	private static Stage stage;
	
	public MatchDetailHandler(Match match) {
		this.match = match;
	}

	public void handle(MouseEvent arg0) {
		UserSession.setActiveMatch(match);
		
		setStage(new Stage());
		stage.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
		stage.show();
	}
	
	private static void setStage(Stage arg0) {
		stage = arg0;
	}
	
	public static Stage getStage() {
		return stage;
	}

}
