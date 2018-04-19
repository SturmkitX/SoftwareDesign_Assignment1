package starters;

import entities.Tournament;
import implementations.hibernate.HibernateUtil;
import implementations.hibernate.TournamentHibernate;

public class Demo {

    public static void main(String[] args) {
        Tournament t = new TournamentHibernate().findTournament(1);
        System.out.println(t.getMatches().size());

        HibernateUtil.getSessionFactory().close();
    }

}