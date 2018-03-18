package interfaces;

import models.User;

public interface UserDAO {
	public User findUser(String email, String password);
	public User insertUser(User user);
	public User updateUser(User user);
	public User deleteUser(String email);
	
	
}
