package database.hibernate;

import database.interfaces.UserDatabase;
import entities.User;
import implementations.hibernate.UserHibernate;
import interfaces.UserDAO;

import java.util.Set;

public class UserDatabaseHibernate implements UserDatabase {

    private static UserDAO dao = new UserHibernate();

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return dao.findUserByEmailAndPassword(email, password);
    }

    @Override
    public User findUserById(int id) {
        return dao.findUserById(id);
    }

    @Override
    public Set<User> findAll() {
        return dao.findAll();
    }

    @Override
    public void insertUser(User user) {
        dao.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        dao.updateUser(user);
    }

    @Override
    public void deleteUser(User user) {
        dao.deleteUser(user);
    }
}
