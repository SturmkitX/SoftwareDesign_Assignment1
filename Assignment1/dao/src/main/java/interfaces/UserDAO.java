package interfaces;

import java.util.List;

import models.User;

/*
 * Contains basic database I/O operations
 * These operations should be used for more advanced
 * (application-dependent rules) in the business layer
 */

public interface UserDAO {
	public User findUserByEmailAndPassword(String email, String password);
	public User findUserById(int id);
	public List<User> findAllUsers(int offset, int limit);
	public void insertUser(User user);
	public void updateUser(User user);
	public void deleteUser(String email);
	public void deleteUser(int id);
	
	
}
