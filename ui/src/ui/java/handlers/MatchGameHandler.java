package handlers;

import java.util.Iterator;
import java.util.List;

import database.GameAccess;
import database.MatchAccess;
import database.UserAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.Game;
import models.Match;
import models.User;
import scenes.layouts.MatchDetailPane;
import session.UserSession;

public class MatchGameHandler implements EventHandler<ActionEvent> {
	
	private int type;
	private Node p1Field, p2Field;
	private List<Node> scoresP1, scoresP2;
	private Match match;
	
	public MatchGameHandler(int type, Match match, Node pField1, Node pField2, List<Node> scoresP1, List<Node> scoresP2) {
		this.type = type;
		this.p1Field = pField1;
		this.p2Field = pField2;
		this.scoresP1 = scoresP1;
		this.scoresP2 = scoresP2;
		this.match = match;
	}

	public void handle(ActionEvent arg0) {
		switch(type) {
		case 1 : updateFields(); break;
		case 2 : deleteFields(); break;
		default : ;
		}
		
	}
	
	private void updateFields() {
		Iterator<Node> it1 = scoresP1.iterator();
		Iterator<Node> it2 = scoresP2.iterator();
		
		boolean isEditable1 = UserSession.getLoggedInUser().getIsAdmin() ||
				UserSession.getLoggedInUser().getId() == match.getP1().getId();
		boolean isEditable2 = UserSession.getLoggedInUser().getIsAdmin() ||
				UserSession.getLoggedInUser().getId() == match.getP2().getId();
				
		int p1Id = 0;
		int p2Id = 0;
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			p1Id = Integer.parseInt(((TextField)p1Field).getText());
			p2Id = Integer.parseInt(((TextField)p2Field).getText());
		} else {
			p1Id = Integer.parseInt(((Text)p1Field).getText());
			p2Id = Integer.parseInt(((Text)p2Field).getText());
		}
		
		User player1 = UserAccess.getUserById(p1Id);
		User player2 = UserAccess.getUserById(p2Id);
		
		for(Game g : match.getGames()) {
			Node node1 = it1.next();
			Node node2 = it2.next();
			
			String p1String;
			String p2String;
			
			if(isEditable1) {
				p1String = ((TextField)node1).getText();
			} else {
				p1String = ((Text)node1).getText();
			}
			
			if(isEditable2) {
				p2String = ((TextField)node2).getText();
			} else {
				p2String = ((Text)node2).getText();
			}
			
			
			g.setP1Score(Integer.parseInt(p1String));
			g.setP2Score(Integer.parseInt(p2String));
			
			GameAccess.updateGame(g);
		}
		
		match.setP1(player1);
		match.setP2(player2);
		
		MatchAccess.updateMatch(match);
		
		MatchDetailHandler.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
	}
	
	private void deleteFields() {
		MatchAccess.deleteMatch(match);
		
		UserSession.setActiveMatch(null);
		MatchDetailHandler.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
		
	}

}
