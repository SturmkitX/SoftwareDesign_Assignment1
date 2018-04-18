package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import drivers.ConnDriver;
import interfaces.UserDAO;
import entities.User;

public class UserDAOImplem implements UserDAO {

    private Connection conn = ConnDriver.getInstance();

    public User findUserByEmailAndPassword(String email, String password) throws SQLException {
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
        float balance = results.getFloat("balance");

        results.close();
        return new User(id, email, password, name, isAdmin, balance, null, null);
    }

    public User findUserById(int id) throws SQLException {
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
        float balance = results.getFloat("balance");

        results.close();
        return new User(id, email, password, name, isAdmin, balance, null, null);
    }

    public void insertUser(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (email, password, name, isadmin, balance) " +
                "VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getName());
        stmt.setBoolean(4, user.isAdmin());
        stmt.setFloat(5, user.getBalance());

        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if(keys.next()) {
            user.setId(keys.getInt(1));
        }
    }

    public void updateUser(User user) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET email = ?, password = ?, " +
                "name = ?, isadmin = ?, balance = ? WHERE id = ?");
        stmt.setString(1, user.getEmail());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getName());
        stmt.setBoolean(4, user.isAdmin());
        stmt.setFloat(5, user.getBalance());
        stmt.setInt(6, user.getId());

        stmt.executeUpdate();
    }

    public void deleteUser(String email) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE email = ?");
        stmt.setString(1, email);

        stmt.executeUpdate();
    }

    public Set<User> findAll() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users");

        ResultSet results = stmt.executeQuery();

        Set<User> users = new HashSet<>();

        while(results.next()) {
            String name = results.getString("name");
            int id = results.getInt("id");
            String email = results.getString("email");
            String pass = results.getString("password");
            boolean isAdmin = results.getBoolean("isadmin");
            float balance = results.getFloat("balance");

            users.add(new User(id, email, pass, name,  isAdmin, balance, null, null));
        }

        results.close();

        return users;
    }

    public void deleteUser(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE id = ?");
        stmt.setInt(1, id);

        stmt.executeUpdate();
    }

}