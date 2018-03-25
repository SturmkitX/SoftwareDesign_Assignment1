package handlers;

import database.TournamentAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import scenes.layouts.TournamentMatchPane;
import scenes.layouts.TournamentPane;
import session.UserSession;
import starter.MainScreen;

public class TournamentControlHandler implements EventHandler<ActionEvent> {
	
	private final int type;
	private TextField name;
	
	public TournamentControlHandler(int type, Node nameField) {
		this.type = type;
		this.name = (TextField)nameField;
	}

	public void handle(ActionEvent arg0) {
		switch(type) {
		case 1 : {
			UserSession.getActiveTournament().setName(name.getText());
			TournamentAccess.updateTournament(UserSession.getActiveTournament());
			
			// refresh tournament data
			UserSession.refreshActiveTournament();
			MainScreen.setScene(new Scene(new TournamentMatchPane(), 1024, 768));
		}; break;
		
		default : {
			TournamentAccess.deleteTournament(UserSession.getActiveTournament().getId());
			UserSession.setActiveTournament(null);
			MainScreen.setScene(new Scene(new TournamentPane(), 1024, 768));
		}
		}
		
		
		
	}

}
