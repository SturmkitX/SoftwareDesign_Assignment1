package implementations.hibernate;

import entities.Game;
import interfaces.GameDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class GameHibernate implements GameDAO {
    @Override
    public Game findGame(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Game g = (Game) session.get(Game.class, new Integer(id));

        session.close();

        return g;
    }

    @Override
    public Set<Game> findGameByMatchId(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Query query = session.createQuery("from Games where match_id = :match_id");
        query.setInteger("match_id", id);

        Set<Game> g = new HashSet<>(query.list());

        session.close();

        return g;
    }

    @Override
    public void insertGame(Game game) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(game);

        tx.commit();

        session.close();
    }

    @Override
    public void updateGame(Game game) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.update(game);

        tx.commit();

        session.close();
    }

    @Override
    public void deleteGame(Game game) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.delete(game);

        tx.commit();

        session.close();
    }
}
