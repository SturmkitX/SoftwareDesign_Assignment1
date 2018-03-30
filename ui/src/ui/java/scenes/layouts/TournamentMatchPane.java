package scenes.layouts;

import java.util.ArrayList;
import java.util.List;

import database.TournamentAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Match;
import models.Tournament;
import session.UserSession;
import starter.MainScreen;

public class TournamentMatchPane extends GridPane {
	
	private Text idField;
	private Node nameField;
	private Tournament tournament;
	private List<VBox> matchTexts;
	private Button quarterButton, semifinalButton, finalButton;
	private Button updateTour, deleteTour;
	
	private static Stage stage;

	public TournamentMatchPane() {
		super();
		tournament = UserSession.getActiveTournament();
		
		setAlignment(Pos.CENTER);
		
		// setStyle("-fx-grid-lines-visible: true");
		
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		MainScreen.getStage().setTitle("Tournament details");
		
		setUpLabels();
		setUpMatches();
		setUpControls();
		
	}
	
	private void setUpLabels() {

		Text details = new Text("Tournament details");
		details.setFont(Font.font(40));
		add(details, 0, 0, 4, 3);
		
		Label idLabel = new Label("Tournament ID: ");		
		Label nameLabel = new Label("Tournament Name: ");
		
		idField = new Text("" + tournament.getId());
		
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			nameField = new TextField(tournament.getName());
		} else {
			nameField = new Text(tournament.getName());
		}
		
		
		add(idLabel, 0, 3, 2, 1);		
		add(nameLabel, 0, 4, 2, 1);
		
		add(idField, 2, 3, 2, 1);
		add(nameField, 2, 4, 2, 1);
	}

	private void setUpMatches() {
		matchTexts = new ArrayList<VBox>();
		quarterButton = new Button("Add match");
		semifinalButton = new Button("Add match");
		finalButton = new Button("Add match");
		
		Text stage1 = new Text("Quarters");
		stage1.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
		Text semifinals = new Text("Semifinals");
		semifinals.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
		Text finals = new Text("Finals");
		finals.setFont(Font.font("Monospaced", FontWeight.BOLD, 30));
		
		int[] cols = new int[3];
		
		add(stage1, 0, 6, 4, 1);
		add(semifinals, 0, 9, 4, 1);
		add(finals, 0, 12, 4, 1);
		
		for(Match m : tournament.getMatches()) {
			VBox v = new VBox();
			v.getChildren().add(new Text(m.getP1().getName()));
			v.getChildren().add(new Text(m.getP2().getName()));
			
			switch(m.getStage()) {
			case 1 : add(v, cols[0], 7, 1, 2); cols[0]++; break;
			case 2 : add(v, cols[1], 10, 2, 2); cols[1] += 2; break;
			default : add(v, cols[2], 13, 4, 2); cols[3] += 4;
			}
			matchTexts.add(v);
			v.setOnMousePressed(new MatchDetailHandler(m));
		}
		
		// see if all matches are present
		// if not, the admin can add more
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			if(cols[0] < 4) {
				add(quarterButton, cols[0], 7, 1, 2);
				cols[0]++;
			}
			
			if(cols[1] < 4) {
				add(semifinalButton, cols[1], 10, 2, 2);
				cols[1] += 2;
			}
			
			if(cols[2] < 4) {
				add(finalButton, cols[2], 13, 4, 2);
				cols[2] += 4;
			}
		}
		
		// add button handlers
		quarterButton.setOnAction(new MatchHandler(1));
		semifinalButton.setOnAction(new MatchHandler(2));
		finalButton.setOnAction(new MatchHandler(3));
	}

	private void setUpControls() {
		updateTour = new Button("Update Tournament");
		deleteTour = new Button("Delete Tournament");
		
		if(UserSession.getLoggedInUser().getIsAdmin()) {
			add(updateTour, 0, 17, 2, 1);
			add(deleteTour, 2, 17, 2, 1);
		}
		
		
		updateTour.setOnAction(new TournamentControlHandler(1, nameField));
		deleteTour.setOnAction(new TournamentControlHandler(2, null));
	}
	
	private static void setStage(Stage arg0) {
		stage = arg0;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static void setScene(Scene scene) {
		stage.setScene(scene);
	}
	
	private class MatchHandler implements EventHandler<ActionEvent> {
		
		private int type;
		
		public MatchHandler(int type) {
			this.type = type;
		}

		public void handle(ActionEvent arg0) {
			Stage stage = new Stage();
			setStage(stage);
			
			stage.setTitle("Add Match");
			
			stage.setScene(new Scene(new MatchAddPane(type)));
			stage.show();
			
		}
		
	}

	private class TournamentControlHandler implements EventHandler<ActionEvent> {

		private final int type;
		private Node nameField;
		
		public TournamentControlHandler(int type, Node nameField) {
			this.type = type;
			this.nameField = nameField;
		}

		public void handle(ActionEvent arg0) {
			switch(type) {
			case 1 : {
				if(UserSession.getLoggedInUser().getIsAdmin()) {
					TextField name = (TextField)nameField;
					UserSession.getActiveTournament().setName(name.getText());
				} else {
					Text name = (Text)nameField;
					UserSession.getActiveTournament().setName(name.getText());
				}
				
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

	private class MatchDetailHandler implements EventHandler<MouseEvent> {
		private Match match;
		
		public MatchDetailHandler(Match match) {
			this.match = match;
		}

		public void handle(MouseEvent arg0) {
			UserSession.setActiveMatch(match);
			
			// setStage(new Stage());
			stage.setScene(new Scene(new MatchDetailPane(match), 1024, 768));
			stage.show();
		}
	}
}
