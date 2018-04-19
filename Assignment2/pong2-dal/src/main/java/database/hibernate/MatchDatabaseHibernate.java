package database.hibernate;

import database.interfaces.MatchDatabase;
import entities.Match;
import implementations.hibernate.MatchHibernate;
import interfaces.MatchDAO;

public class MatchDatabaseHibernate implements MatchDatabase {

    private static MatchDAO dao = new MatchHibernate();

    @Override
    public Match findMatch(int id) {
        return dao.findMatch(id);
    }

    @Override
    public void insertMatch(Match match) {
        dao.insertMatch(match);
    }

    @Override
    public void updateMatch(Match match) {
        dao.updateMatch(match);
    }

    @Override
    public void deleteMatch(Match match) {
        dao.deleteMatch(match);
    }
}
