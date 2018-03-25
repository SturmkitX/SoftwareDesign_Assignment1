package scenes.layouts;

import java.util.ArrayList;
import java.util.List;

import handlers.MatchDetailHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.GameLogic;
import models.Game;
import models.Match;
import session.UserSession;

public class MatchDetailPane extends GridPane {
	
	private Match match;
	private Node idField1, idField2;
	private List<Node> scoresP1;
	private List<Node> scoresP2;
	private Button addGameBtn;
	private Button updateGame, deleteGame;
	
	public MatchDetailPane(Match match) {
		super();
		this.match = match;
		
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		MatchDetailHandler.getStage().setTitle("Match details");
		
		int winStatus = 0;
		
		setUpHeader();
		setGamesPlayed();
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
		int gameRow = 5;
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

}
