package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import drivers.ConnDriver;
import entities.Match;
import entities.Tournament;
import interfaces.UserDAO;
import entities.User;

public class UserDAOImplem implements UserDAO {

    private Connection conn = ConnDriver.getInstance();

    public User findUserByEmailAndPassword(String email, String password) {
        User result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND password = ?");
            stmt.setString(1, email);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                stmt.close();
                return null;
            }

            int id = rs.getInt("id");
            String name = rs.getString("name");
            boolean admin = rs.getBoolean("isadmin");
            float balance = rs.getFloat("balance");

            rs.close();
            stmt.close();

            result = new User(id, email, password, name, admin, balance, new HashSet<Match>(), new HashSet<Match>(), new HashSet<Tournament>());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public User findUserById(int id) {
        User result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                stmt.close();
                return null;
            }

            String email = rs.getString("email");
            String password = rs.getString("password");
            String name = rs.getString("name");
            boolean admin = rs.getBoolean("isadmin");
            float balance = rs.getFloat("balance");
            
            rs.close();
            stmt.close();

            result = new User(id, email, password, name, admin, balance, new HashSet<Match>(), new HashSet<Match>(), new HashSet<Tournament>());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void insertUser(User user) {
        try {
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

            keys.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateUser(User user) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET email = ?, password = ?, " +
                    "name = ?, isadmin = ?, balance = ? WHERE id = ?");
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getName());
            stmt.setBoolean(4, user.isAdmin());
            stmt.setFloat(5, user.getBalance());
            stmt.setInt(6, user.getId());

            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement("DELETE * FROM UserTournament WHERE id_user = ?");
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement("INSERT INTO UserTournament (id_user, id_tournament) VALUES (?, ?)");
            stmt.setInt(1, user.getId());
            for(Tournament t : user.getTournaments()) {
                stmt.setInt(2, t.getId());
                stmt.executeUpdate();
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteUser(User user) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Users WHERE email = ?");
            stmt.setString(1, user.getEmail());

            stmt.executeUpdate();
            stmt.close();

            stmt = conn.prepareStatement("DELETE * FROM UserTournament WHERE id_user = ?");
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Set<User> findAll() {
        Set<User> result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users");
            ResultSet rs = stmt.executeQuery();

            result = new HashSet<>();

            while(rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String pass = rs.getString("password");
                boolean isAdmin = rs.getBoolean("isadmin");
                float balance = rs.getFloat("balance");

                result.add(new User(id, email, pass, name,  isAdmin, balance, new HashSet<Match>(), new HashSet<Match>(), new HashSet<Tournament>()));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}