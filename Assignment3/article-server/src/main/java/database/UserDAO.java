package database;

import user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final Connection conn = ConnDriver.getInstance();

    private UserDAO() {

    }

    public static User findUserAuthentication(String email, String pass) {
        User result = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email=? AND password=?");
            stmt.setString(1, email);
            stmt.setString(2, pass);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) {
                return null;
            }

            result = new User();
            result.setId(rs.getInt("id"));
            result.setEmail(email);
            result.setPassword(pass);
            result.setName(rs.getString("name"));
            result.setRole(rs.getInt("role"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
