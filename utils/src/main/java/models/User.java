package models;

public class User {
	private int id;
	private String email;
	private String password;
	private boolean isAdmin;
	
	public User(int id, String email, String password, boolean isAdmin) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.isAdmin = isAdmin;
	}
	
	public int getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean getIsAdmin() {
		return isAdmin;
	}
	
	public String toString() {
		return String.format("ID: %s\nE-mail: %s\nPass: %s\nAdmin: %b\n", id, email, password, isAdmin);
	}
}
