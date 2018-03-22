package scenes.layouts;

import database.UserAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import models.User;
import session.UserSession;
import starter.MainScreen;

public class LogInPane extends GridPane {
	
	private Text title;
	private TextField userField;
	private PasswordField passField;
	private Button button;
	private Text status;

	public LogInPane() {
		super();
		setAlignment(Pos.CENTER);
		setHgap(10);
		setVgap(10);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		// set title
		title = new Text("Welcome! Please, Log In");
		add(title, 0, 0, 2, 1);
		title.setFont(Font.font(25));
		
		// add username field
		Label userLabel = new Label("E-mail: ");
		userField = new TextField();
		add(userLabel, 0, 2);
		add(userField, 1, 2);
		
		// add password field
		Label passLabel = new Label("Password: ");
		passField = new PasswordField();
		add(passLabel, 0, 3);
		add(passField, 1, 3);
		
		// add sign-in button
		button = new Button("Log In");
		HBox hBtn = new HBox();
		hBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hBtn.getChildren().add(button);
		
		button.setOnAction(getButtonEventHandler());
		add(hBtn, 1, 5);
		
		// add status text
		status = new Text();
		add(status, 1, 8);
		
		
	}
	
	private EventHandler<ActionEvent> getButtonEventHandler() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				String email = userField.getText();
				String password = passField.getText();
				
				User logUser = UserAccess.getUserByEmailAndPassword(email, password);
				UserSession.setLoggedInUser(logUser);
				if(logUser == null) {
					status.setFill(Color.RED);
					status.setText("Invalid user information!");
				} else {
					status.setFill(Color.GREEN);
					status.setText("Welcome, " + logUser.getName());
					MainScreen.setScene(new Scene(new TournamentPane(), 600, 400));
				}
				
			}
			
		};
	}
	
}
