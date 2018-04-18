package implementations.hibernate;

import entities.User;
import interfaces.UserDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class UserHibernate implements UserDAO {
    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

//        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("from Users where email = :email and password = :password");
        query.setString("email", email);
        query.setString("password", password);

        User u = (User) query.uniqueResult();

//        tx.rollback();
        session.close();
//        sessionFactory.close();

        return u;
    }

    @Override
    public User findUserById(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        User u = (User) session.get(User.class, new Integer(id));

        session.close();

        return u;
    }

    @Override
    public Set<User> findAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Users");

        Set<User> u = new HashSet<>(query.list());

        session.close();

        return u;
    }

    @Override
    public void insertUser(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(user);

        tx.commit();

        session.close();
    }

    @Override
    public void updateUser(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.update(user);

        tx.commit();

        session.close();
    }

    @Override
    public void deleteUser(User user) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.delete(user);

        tx.commit();

        session.close();
    }

}
