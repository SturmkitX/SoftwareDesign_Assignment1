package scenes.layouts;

import java.util.List;

import database.UserAccess;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;

public class UsersViewPane extends GridPane {
	
	private Button addUserBtn;
	
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
	}
}
