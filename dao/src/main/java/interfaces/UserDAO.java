package interfaces;

import models.User;

/*
 * Contains basic database I/O operations
 * These operations should be used for more advanced
 * (application-dependent rules) in the business layer
 */

public interface UserDAO {
	public User findUser(String email, String password);
	public void insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(String email);
	
	
}
