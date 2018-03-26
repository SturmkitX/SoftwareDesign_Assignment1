package handlers;


import database.UserAccess;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.User;
import scenes.layouts.UserEditPane;
import starter.MainScreen;

public class UserInfoHandler implements EventHandler<MouseEvent>{

	public void handle(MouseEvent arg0) {
		// TODO Auto-generated method stub
		VBox v = (VBox)arg0.getSource();
		int id = 0;
				
		for(Node n : v.getChildren()) {
			Text t = (Text)n;
			if(t.getText().contains("ID: ")) {
				id = Integer.parseInt(t.getText().substring(4));
				break;
			}
		}
		
		User user = UserAccess.getUserById(id);
		MainScreen.getUserStage().setScene(new Scene(new UserEditPane(user), 1024, 768));
		
	}

}
