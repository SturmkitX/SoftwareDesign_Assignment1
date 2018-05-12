package database;

import user.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public static User findUser(int id) {
        User result = null;

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) {
                return null;
            }

            result = new User();
            result.setId(id);
            result.setEmail(rs.getString("email"));
            result.setPassword(rs.getString("password"));
            result.setName(rs.getString("name"));
            result.setRole(rs.getInt("role"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static List<User> findAllWiters() {
        List<User> result = new ArrayList<>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE role = 1");

            while(rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setRole(1);

                result.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void insertWriter(User user) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (name, email, password, role) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getRole());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                user.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            user.setId(-1);
        }
    }

    public static void updateWriter(User user) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET name = ?, email = ?, password = ?, role = ? WHERE id = ?");
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPassword());
            stmt.setInt(4, user.getRole());
            stmt.setInt(5, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            user.setId(-1);
        }
    }

    public static void deletetWriter(User user) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE id = ?");
            stmt.setInt(1, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            user.setId(-1);
        }
    }
}
