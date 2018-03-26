package scenes.layouts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.UserAccess;
import handlers.UserAddHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import models.User;
import starter.MainScreen;

public class UserAddPane extends GridPane {
	
	private TextField nameField, emailField, passField;
	private ComboBox<String> adminField;
	private Button addUserBtn;
	
	public UserAddPane() {
		super();
		
		setAlignment(Pos.CENTER);
		setHgap(10);
		setVgap(10);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		MainScreen.getUserStage().setTitle("Add User");
	
		setUpFields();
		setUpControls();
	}
	
	private void setUpFields() {
		Label nameLabel = new Label("Name: ");
		nameField = new TextField();
		add(nameLabel, 0, 1);
		add(nameField, 1, 1);
		
		Label emailLabel = new Label("Email: ");
		emailField = new TextField();
		add(emailLabel, 0, 2);
		add(emailField, 1, 2);
		
		Label passLabel = new Label("Password: ");
		passField = new TextField();
		add(passLabel, 0, 3);
		add(passField, 1, 3);
		
		Label adminLabel = new Label("Administrator: ");
		adminField = new ComboBox<String>();
		add(adminLabel, 0, 4);
		add(adminField, 1, 4);
		adminField.getItems().addAll("Regular User", "Administrator");
		adminField.getSelectionModel().selectFirst();
	}
	
	private void setUpControls() {
		addUserBtn = new Button("Add user");
		
		add(addUserBtn, 0, 7, 2, 1);
		
		addUserBtn.setOnAction(getButtonHandler());
	}
	
	private EventHandler<ActionEvent> getButtonHandler() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String name = nameField.getText();
				String email = emailField.getText();
				String pass = passField.getText();
				boolean isAdmin = adminField.getValue().equals("Regular User") ? false : true;
				
				Matcher m = Pattern.compile("\\S+@\\S+\\.\\S+").matcher(email);
				if(m.find()) {
					User user = new User(0, email, pass, name, isAdmin);
					UserAccess.insertUser(user);
					UserAddHandler.getStage().close();
					MainScreen.getUserStage().setScene(new Scene(new UsersViewPane(), 1024, 768));
				}
			}
			
		};
	}
	
	

}
