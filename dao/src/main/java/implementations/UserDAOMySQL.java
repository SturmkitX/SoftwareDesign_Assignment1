package implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import drivers.ConnDriver;
import interfaces.UserDAO;
import models.User;

public class UserDAOMySQL implements UserDAO {
	
	private Connection conn = ConnDriver.getInstance();

	public User findUser(String email, String password) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email=? AND " +
					"password=?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			ResultSet results = stmt.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			boolean isAdmin = results.getBoolean("isadmin");
			int id = results.getInt("id");
			
			results.close();
			return new User(id, email, password, isAdmin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public User insertUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User deleteUser(String email) {
		// TODO Auto-generated method stub
		return null;
	}

}
