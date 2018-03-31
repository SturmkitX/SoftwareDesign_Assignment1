package scenes.layouts;

import java.util.List;

import database.UserAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.User;
import session.UserSession;
import starter.MainScreen;

public class UsersViewPane extends GridPane {
	
	private Button addUserBtn;
	private static Stage stage;
	private Button prevPage, nextPage;
	
	public UsersViewPane() {
		super();
		
		setAlignment(Pos.CENTER);
		setHgap(20);
		setVgap(20);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		setUserView();
		
		
	}
	
	private void setUserView() {
		Text title = new Text("Users");
		title.setFont(Font.font(30));
		
		add(title, 0, 0, 4, 2);
		
		int rows = 2;
		int cols = 0;
		
		List<User> users = UserAccess.getAllUsers();
		EventHandler<MouseEvent> flh = new UserInfoHandler();
		for(User user : users) {
			VBox v = new VBox();
			Text uId = new Text("ID: " + user.getId());
			Text uName = new Text(user.getName());
			
			v.getChildren().add(uId);
			v.getChildren().add(uName);
			
			add(v, cols, rows);
			cols++;
			if(cols == 4) {
				rows++;
				cols = 0;
			}
			
			v.setOnMousePressed(flh);
		}
		
		addUserBtn = new Button("Add user");
		add(addUserBtn, cols, rows);
		
		addUserBtn.setOnAction(new UserAddHandler());
		
		prevPage = new Button("Previous page");
		nextPage = new Button("Next page");
		
		UserPageHandler uph = new UserPageHandler();
		
		prevPage.setOnAction(uph);
		nextPage.setOnAction(uph);
		
		if(UserSession.getUserOffset() > 0) {
			add(prevPage, 1, rows + 2);
		}
		
		if(users.size() == UserSession.getUserLimit()) {
			add(nextPage, 2, rows + 2);
		}
	}
	
	private static void setStage(Stage arg0) {
		stage = arg0;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	private class UserInfoHandler implements EventHandler<MouseEvent> {
		
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
	
	private class UserAddHandler implements EventHandler<ActionEvent> {
		
		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
			setStage(new Stage());
			
			stage.setScene(new Scene(new UserAddPane()));
			stage.setTitle("Add User");
			stage.show();
		}
	}
	
	private class UserPageHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			if(arg0.getSource() == prevPage) {
				UserSession.decrementUserOffset();
			} else if(arg0.getSource() == nextPage) {
				UserSession.incrementUserOffset();
			}
			
			MainScreen.getUserStage().setScene(new Scene(new UsersViewPane(), 1024, 768));
			
		}
		
	}
}
