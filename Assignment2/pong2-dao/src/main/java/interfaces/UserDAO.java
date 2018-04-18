package interfaces;

import java.util.Set;

import entities.User;

/*
 * Contains basic database I/O operations
 * These operations should be used for more advanced
 * (application-dependent rules) in the business layer
 */

public interface UserDAO {
    User findUserByEmailAndPassword(String email, String password);
    User findUserById(int id);
    Set<User> findAll();
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);


}