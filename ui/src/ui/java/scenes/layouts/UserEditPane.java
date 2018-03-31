package scenes.layouts;

import database.UserAccess;
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
import javafx.scene.text.Text;
import models.User;
import starter.MainScreen;

public class UserEditPane extends GridPane {
	
	private User user;
	private Button updateBtn, deleteBtn;
	private TextField nameField, emailField, passField;
	private Text idField;
	private ComboBox<String> adminField;
	private Button backBtn;
	
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
		adminField.getSelectionModel().selectFirst();
	}
	
	private void setUpControls() {
		updateBtn = new Button("Update user");
		deleteBtn = new Button("Delete user");
		
		add(updateBtn, 0, 7);
		add(deleteBtn, 1, 7);
		
		updateBtn.setOnAction(new UserEditHandler(1));
		deleteBtn.setOnAction(new UserEditHandler(2));
		
		backBtn = new Button("Back");
		add(backBtn, 0, 9, 2, 1);
		
		backBtn.setOnAction(new BackButtonHandler());
	}
	
	private class UserEditHandler implements EventHandler<ActionEvent> {

		private int type;
		
		public UserEditHandler(int type) {
			this.type = type;
		}

		public void handle(ActionEvent arg0) {
			// TODO Auto-generated method stub
			switch(type) {
			case 1 : updateFields(); break;
			default : deleteFields();
			}
			
		}
		
		private void updateFields() {
			int id = Integer.parseInt(idField.getText());
			String name = nameField.getText();
			String pass = passField.getText();
			String email = emailField.getText();
			boolean isAdmin = adminField.getValue().equals("Regular User") ? false : true;
			
			
			UserAccess.updateUser(new User(id, email, pass, name, isAdmin));
		}
		
		private void deleteFields() {
			int id = Integer.parseInt(idField.getText());
			UserAccess.deleteUser(id);
			MainScreen.getUserStage().setScene(new Scene(new UsersViewPane(), 1024, 768));
		}
		
	}
	
	private class BackButtonHandler implements EventHandler<ActionEvent> {

		public void handle(ActionEvent arg0) {
			MainScreen.getUserStage().setScene(new Scene(new UsersViewPane(), 1024, 768));
			
		}
		
	}
	
	

}
