package handlers;

import database.UserAccess;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.User;

public class UserEditHandler implements EventHandler<ActionEvent> {
	
	private int type;
	private Text idField;
	private TextField nameField, emailField, passField;
	private ComboBox<String> adminField;
	
	public UserEditHandler(int type, Text idField, TextField nameField, TextField emailField,
			TextField passField, ComboBox<String> adminField) {
		this.type = type;
		this.idField = idField;
		this.nameField = nameField;
		this.emailField = emailField;
		this.passField = passField;
		this.adminField = adminField;
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
	}

}
