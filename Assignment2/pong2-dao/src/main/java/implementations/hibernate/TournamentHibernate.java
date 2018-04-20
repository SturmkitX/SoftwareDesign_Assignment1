package implementations.hibernate;

import entities.Tournament;
import interfaces.TournamentDAO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.Set;

public class TournamentHibernate implements TournamentDAO {
    @Override
    public Tournament findTournament(int id) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Tournament t = (Tournament) session.get(Tournament.class, new Integer(id));

        tx.commit();

        return t;
    }

    @Override
    public Set<Tournament> findAll() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Tournament");

        Set<Tournament> u = new HashSet<>(query.list());

        tx.commit();

        return u;
    }

    @Override
    public Tournament findTournamentByName(String name) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Tournament where name = :name");
        query.setString("name", name);

        Tournament t = (Tournament) query.uniqueResult();

        tx.commit();

        return t;
    }

    @Override
    public void insertTournament(Tournament tournament) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.save(tournament);

        tx.commit();
    }

    @Override
    public void updateTournament(Tournament tournament) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.update(tournament);

        tx.commit();

    }

    @Override
    public void deleteTournament(Tournament tournament) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        session.delete(tournament);

        tx.commit();

    }
}
