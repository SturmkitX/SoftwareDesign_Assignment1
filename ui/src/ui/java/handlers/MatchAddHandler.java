package handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.layouts.MatchAddPane;

public class MatchAddHandler implements EventHandler<ActionEvent> {
	
	private final int type;
	private static Stage stage;
	
	public MatchAddHandler(int type) {
		this.type = type;
	}

	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Stage stage = new Stage();
		setStage(stage);
		
		stage.setTitle("Add Match");
		
		stage.setScene(new Scene(new MatchAddPane(type)));
		stage.show();
	}
	
	private static void setStage(Stage arg0) {
		stage = arg0;
	}
	
	public static Stage getStage() {
		return stage;
	}

}
