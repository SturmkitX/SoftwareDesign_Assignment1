package handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import scenes.layouts.UserAddPane;

public class UserAddHandler implements EventHandler<ActionEvent> {
	
	private static Stage stage;

	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
		setStage(new Stage());
		
		stage.setScene(new Scene(new UserAddPane()));
		stage.setTitle("Add User");
		stage.show();
	}
	
	private static void setStage(Stage arg0) {
		stage = arg0;
	}
	
	public static Stage getStage() {
		return stage;
	}

}
