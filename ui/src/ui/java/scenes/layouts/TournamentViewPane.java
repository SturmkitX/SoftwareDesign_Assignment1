package scenes.layouts;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.Tournament;

public class TournamentViewPane extends GridPane {

	public TournamentViewPane(Tournament tournament) {
		super();
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		Label idLabel = new Label("Tournament ID: ");
		Text idText = new Text("" + tournament.getId());
		
		Label nameLabel = new Label("Tournament Name: ");
		Text nameText = new Text(tournament.getName());
		
		add(idLabel, 0, 0);
		add(idText, 1, 0);
		
		add(nameLabel, 0, 1);
		add(nameText, 1, 1);
		
	}
}
