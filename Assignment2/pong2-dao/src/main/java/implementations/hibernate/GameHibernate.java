package implementations.hibernate;

import entities.Game;
import interfaces.GameDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GameHibernate implements GameDAO {
    @Override
    public Game findGame(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Game g = (Game) session.get(Game.class, new Integer(id));

        return g;
    }

    @Override
    public void insertGame(Game game) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(game);

        tx.commit();
    }

    @Override
    public void updateGame(Game game) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.update(game);

        tx.commit();
    }

    @Override
    public void deleteGame(Game game) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.delete(game);

        tx.commit();
    }
}
