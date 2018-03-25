package scenes.layouts;

import handlers.UserEditHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import models.User;
import starter.MainScreen;

public class UserEditPane extends GridPane {
	
	private User user;
	private Button updateBtn, deleteBtn;
	private TextField nameField, emailField, passField;
	private Text idField;
	private ComboBox<String> adminField;
	
	public UserEditPane(User user) {
		super();
		
		this.user = user;
		
		setAlignment(Pos.CENTER);
		setHgap(10);
		setVgap(10);
		
		setPadding(new Insets(25, 25, 25, 25));
		
		MainScreen.getUserStage().setTitle("Edit User");
	
		setUpFields();
		setUpControls();
	}
	
	private void setUpFields() {
		Label idLabel = new Label("ID: ");
		idField = new Text("" + user.getId());
		add(idLabel, 0, 0);
		add(idField, 1, 0);
		
		Label nameLabel = new Label("Name: ");
		nameField = new TextField(user.getName());
		add(nameLabel, 0, 1);
		add(nameField, 1, 1);
		
		Label emailLabel = new Label("Email: ");
		emailField = new TextField(user.getEmail());
		add(emailLabel, 0, 2);
		add(emailField, 1, 2);
		
		Label passLabel = new Label("Password: ");
		passField = new TextField(user.getPassword());
		add(passLabel, 0, 3);
		add(passField, 1, 3);
		
		Label adminLabel = new Label("Administrator: ");
		adminField = new ComboBox<String>();
		add(adminLabel, 0, 4);
		add(adminField, 1, 4);
		adminField.getItems().addAll("Regular User", "Administrator");
	}
	
	private void setUpControls() {
		updateBtn = new Button("Update user");
		deleteBtn = new Button("Delete user");
		
		add(updateBtn, 0, 7);
		add(deleteBtn, 1, 7);
		
		updateBtn.setOnAction(new UserEditHandler(1, idField, nameField, emailField, passField, adminField));
		deleteBtn.setOnAction(new UserEditHandler(2, idField, null, null, null, null));
	}
	
	

}
