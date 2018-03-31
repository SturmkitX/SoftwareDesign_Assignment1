package scenes.layouts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import database.GameAccess;
import database.MatchAccess;
import database.UserAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameLogic;
import logic.MatchLogic;
import models.Game;
import models.Match;
import models.User;
import session.UserSession;

public class MatchDetailPane extends GridPane {
	
	private Match match;
	private Node idField1, idField2;
	private List<Node> scoresP1;
	private List<Node> scoresP2;
	private Button addGameBtn;
	private Button updateMatch, deleteMatch;
	private int gameRow;
	private Text updateStatus;
	
	public MatchDetailPane(Match match) {
		super();
		this.match = match;
		
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		TournamentMatchPane.getStage().setTitle("Match details");
		
		gameRow = 0;
		
		setUpHeader();
		setGamesPlayed();
		setControls();
	}
	
	private void setUpHeader() {
		Label titleLabel = new Label("Match Details");
		titleLabel.setFont(Font.font(30));
		add(titleLabel, 0, 0, 4, 2);
		
		Label idLabel1 = new Label("Player 1 ID: ");
		Label idLabel2 = new Label("Player 2 ID: ");
		
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			idField1 = new TextField("" + match.getP1().getId());
			idField2 = new TextField("" + match.getP2().getId());
		} else {
			idField1 = new Text("" + match.getP1().getId());
			idField2 = new Text("" + match.getP2().getId());
		}
		
		Label nameLabel1 = new Label("Player 1 Name: ");
		Label nameLabel2 = new Label("Player 2 Name: ");
		
		Text nameField1 = new Text("" + match.getP1().getName());
		Text nameField2 = new Text("" + match.getP2().getName());
		
		add(idLabel1, 0, 2);
		add(idField1, 1, 2);
		
		add(idLabel2, 2, 2);
		add(idField2, 3, 2);
		
		add(nameLabel1, 0, 3);
		add(nameField1, 1, 3);
		
		add(nameLabel2, 2, 3);
		add(nameField2, 3, 3);
	}
	
	private void setGamesPlayed() {
		gameRow = 5;
		int gameNumber = 0;
		scoresP1 = new ArrayList<Node>();
		scoresP2 = new ArrayList<Node>();
		
		for(Game g : match.getGames()) {
			Text t = new Text("Game #" + (gameNumber + 1) + " results");
			add(t, 0, gameRow, 4, 1);
			
			Node p1Score;
			Node p2Score;
			
			if(UserSession.getLoggedInUser().getId() == match.getP1().getId() || 
					UserSession.getLoggedInUser().getIsAdmin()) {
				p1Score = new TextField("" + g.getP1Score());
			} else {
				p1Score = new Text("" + g.getP1Score());
			}
			
			if(UserSession.getLoggedInUser().getId() == match.getP2().getId() || 
					UserSession.getLoggedInUser().getIsAdmin()) {
				p2Score = new TextField("" + g.getP2Score());
			} else {
				p2Score = new Text("" + g.getP2Score());
			}
			
			// draw the score box
			add(p1Score, 1, gameRow + 1);
			add(p2Score, 2, gameRow + 1);
			
			scoresP1.add(p1Score);
			scoresP2.add(p2Score);
			
			int gameWinner = GameLogic.getWinner(g);
			if(gameWinner == 1) {
				Text gWinner = new Text("Winner");
				gWinner.setFill(Color.GREEN);
				add(gWinner, 0, gameRow + 1);
			}
			
			if(gameWinner == 2) {
				Text gWinner = new Text("Winner");
				gWinner.setFill(Color.GREEN);
				add(gWinner, 3, gameRow + 1);
			}
			
			gameRow += 3;
			gameNumber++;
		}
	}
	
	private void setControls() {
		int winStatus = MatchLogic.getWinner(match);
		addGameBtn = new Button("Add game");
		if(winStatus == 0 && UserSession.getLoggedInUser().getIsAdmin() && match.getGames().size() < 5) {
			add(addGameBtn, 1, gameRow, 2, 1);
		} else {
			String winner = (winStatus == 1) ? match.getP1().getName() : match.getP2().getName();
			winner = (winStatus == 0) ? "None" : winner;
			Text winField = new Text("Winner: " + winner);
			winField.setFill(Color.GREEN);
			winField.setFont(Font.font(24));
			add(winField, 1, gameRow, 2, 1);
		}
		
		gameRow += 2;
		
		
		// update the update button
		updateMatch = new Button("Update Match");
		if(UserSession.getLoggedInUser().getIsAdmin() || UserSession.getLoggedInUser().getId() == match.getP1().getId() || 
				UserSession.getLoggedInUser().getId() == match.getP2().getId()) {
			add(updateMatch, 1, gameRow);
		}
		
		// add the delete button
		deleteMatch = new Button("Delete Match");
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			add(deleteMatch, 2, gameRow);
		}
		
		// add handlers
		addGameBtn.setOnAction(new GameAddHandler());
		updateMatch.setOnAction(new MatchGameHandler(1));
		deleteMatch.setOnAction(new MatchGameHandler(2));
		
		// add update status
		updateStatus = new Text();
		updateStatus.setFill(Color.FIREBRICK);
		add(updateStatus, 1, gameRow + 2, 2, 1);
		
	}
	
	private class GameAddHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			Game g = new Game(0, 0, 0);
			GameAccess.insertGame(g);
			
			match.getGames().add(g);
			TournamentMatchPane.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
			
		}
		
	}
	
	private class MatchGameHandler implements EventHandler<ActionEvent> {
		
		private int type;
		
		public MatchGameHandler(int type) {
			this.type = type;
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
				p1Id = Integer.parseInt(((TextField)idField1).getText());
				p2Id = Integer.parseInt(((TextField)idField2).getText());
			} else {
				p1Id = Integer.parseInt(((Text)idField1).getText());
				p2Id = Integer.parseInt(((Text)idField2).getText());
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
				
				
				int p1Int = 0;
				int p2Int = 0;
				try {
					p1Int = Integer.parseInt(p1String);
					p2Int = Integer.parseInt(p2String);
				} catch(NumberFormatException e) {
					updateStatus.setText("All fields must be positive integers");
					return;
				}
				
				if(p1Int < 0 || p2Int < 0) {
					updateStatus.setText("All fields must be positive integers");
					return;
				}
				
				g.setP1Score(p1Int);
				g.setP2Score(p2Int);
				
				GameAccess.updateGame(g);
			}
			
			match.setP1(player1);
			match.setP2(player2);
			
			MatchAccess.updateMatch(match);
			
			TournamentMatchPane.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
		}
		
		private void deleteFields() {
			MatchAccess.deleteMatch(match);
			
			UserSession.setActiveMatch(null);
			TournamentMatchPane.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
			
		}
		
	}

}
