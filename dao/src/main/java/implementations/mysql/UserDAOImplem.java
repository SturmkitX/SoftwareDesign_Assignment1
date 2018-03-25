package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import drivers.ConnDriver;
import interfaces.UserDAO;
import models.User;

public class UserDAOImplem implements UserDAO {
	
	private Connection conn = ConnDriver.getInstance();

	public User findUserByEmailAndPassword(String email, String password) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email=? " +
					"AND password=?");
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
			String name = results.getString("name");
			
			results.close();
			return new User(id, email, password, name, isAdmin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public User findUserById(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE id=?");
			stmt.setInt(1, id);
			
			ResultSet results = stmt.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			boolean isAdmin = results.getBoolean("isadmin");
			String email = results.getString("email");
			String name = results.getString("name");
			String password = results.getString("password");
			
			results.close();
			return new User(id, email, password, name, isAdmin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void insertUser(User user) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (email, password, isadmin) " +
					"VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setBoolean(3, user.getIsAdmin());
			
			stmt.executeUpdate();
			
			ResultSet keys = stmt.getGeneratedKeys();
			if(keys.next()) {
				user.setId(keys.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateUser(User user) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET email = ?, password = ? " + 
					"WHERE id = ?");
			stmt.setString(1, user.getEmail());
			stmt.setString(2, user.getPassword());
			stmt.setInt(3, user.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteUser(String email) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE email = ?");
			stmt.setString(1, email);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
