package database;

import implementations.mysql.UserDAOImplem;
import interfaces.UserDAO;
import models.User;

public final class UserAccess {
	
	// private fields
	private static UserDAO dao = new UserDAOImplem();
	
	// make the class un-instantiable
	private UserAccess() {
		
	}
	
	public static User getUserByEmailAndPassword(String email, String password) {
		return dao.findUserByEmailAndPassword(email, password);
		
	}
}
