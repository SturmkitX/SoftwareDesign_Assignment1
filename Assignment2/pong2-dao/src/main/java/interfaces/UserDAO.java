package interfaces;

import java.sql.SQLException;
import java.util.List;

import entities.User;

/*
 * Contains basic database I/O operations
 * These operations should be used for more advanced
 * (application-dependent rules) in the business layer
 */

public interface UserDAO {
    public User findUserByEmailAndPassword(String email, String password) throws SQLException;
    public User findUserById(int id) throws SQLException;
    public List<User> findAllUsers(int offset, int limit) throws SQLException;
    public void insertUser(User user) throws SQLException;
    public void updateUser(User user) throws SQLException;
    public void deleteUser(String email) throws SQLException;
    public void deleteUser(int id) throws SQLException;


}