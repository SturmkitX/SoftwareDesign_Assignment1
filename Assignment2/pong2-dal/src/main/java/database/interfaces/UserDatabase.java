package database.interfaces;

import entities.User;

import java.util.Set;

public interface UserDatabase {
    User findUserByEmailAndPassword(String email, String password);
    User findUserById(int id);
    Set<User> findAll();
    void insertUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
