package implementations.hibernate;

import java.util.HashSet;
import java.util.Set;

import entities.Game;
import entities.Match;
import entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class HibernateMain {

    public static void main(String[] args) {

        Match match = new Match();

        Game g1 = new Game(0, 5, 10);
        Game g2 = new Game(1, 10, 7);
        Set<Game> gameSet = new HashSet<>();
        gameSet.add(g1); gameSet.add(g2);

        User u1 = new User(5, "dwao", "fjawj", "fiwah", false, 40, null, null);
        User u2 = new User(10, "fjwai", "f9awhh", "y8qyhg", false, 200, null, null);

        match.setGames(gameSet);
        match.setStage(0);
        match.setP1(u1);
        match.setP2(u2);

        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction tx = null;
        try{
            //Get Session
            sessionFactory = HibernateUtil.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            System.out.println("Session created");
            //start transaction
            tx = session.beginTransaction();

            //Save the Model objects
            session.save(match);
            session.save(g1);
            session.save(g2);
            session.save(u1);
            session.save(u2);

            //Commit transaction
            tx.commit();
            System.out.println("Match ID=" + match.getId());

        }catch(Exception e){
            System.out.println("Exception occured. "+e.getMessage());
            e.printStackTrace();
        }finally{
            if(!sessionFactory.isClosed()){
                System.out.println("Closing SessionFactory");
                sessionFactory.close();
            }
        }
    }

}