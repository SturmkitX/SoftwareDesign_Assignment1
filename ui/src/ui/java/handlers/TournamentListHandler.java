package handlers;

import database.TournamentAccess;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import models.Tournament;
import scenes.layouts.TournamentMatchPane;
import session.UserSession;
import starter.MainScreen;

public class TournamentListHandler implements EventHandler<MouseEvent> {

	public void handle(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Text t = (Text) arg0.getSource();
		Tournament tournament = TournamentAccess.getTournamentByName(t.getText());
		
		UserSession.setActiveTournament(tournament);
		
		MainScreen.setScene(new Scene(new TournamentMatchPane(), 1024, 768));
	}

}
