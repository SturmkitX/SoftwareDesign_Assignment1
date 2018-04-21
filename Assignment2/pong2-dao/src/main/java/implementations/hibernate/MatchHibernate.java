package implementations.hibernate;

import entities.Match;
import interfaces.MatchDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MatchHibernate implements MatchDAO {
    @Override
    public Match findMatch(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        Match m = (Match) session.get(Match.class, new Integer(id));

        return m;
    }

    @Override
    public void insertMatch(Match match) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(match);

        tx.commit();
    }

    @Override
    public void updateMatch(Match match) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.update(match);

        tx.commit();
    }

    @Override
    public void deleteMatch(Match match) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.delete(match);

        tx.commit();
    }
}
