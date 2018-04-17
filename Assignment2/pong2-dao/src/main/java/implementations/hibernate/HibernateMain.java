package implementations.hibernate;

import java.util.HashSet;
import java.util.Set;

import entities.Game;
import entities.Match;
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

        match.setGames(gameSet);
        cart.setTotal(10*1 + 20*2);

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
            session.save(cart);
            session.save(item1);
            session.save(item2);

            //Commit transaction
            tx.commit();
            System.out.println("Cart ID="+cart.getId());

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