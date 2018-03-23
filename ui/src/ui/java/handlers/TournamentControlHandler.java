package handlers;

import database.TournamentAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import scenes.layouts.TournamentMatchPane;
import session.UserSession;
import starter.MainScreen;

public class TournamentControlHandler implements EventHandler<ActionEvent> {
	
	private final int type;
	
	public TournamentControlHandler(int type) {
		this.type = type;
	}

	public void handle(ActionEvent arg0) {
		switch(type) {
		case 1 : TournamentAccess.updateTournament(UserSession.getActiveTournament()); break;
		default : TournamentAccess.deleteTournament(UserSession.getActiveTournament().getId());
		}
		
		// refresh tournament data
		UserSession.refreshActiveTournament();
		MainScreen.setScene(new Scene(new TournamentMatchPane(), 1024, 768));
		
	}

}
