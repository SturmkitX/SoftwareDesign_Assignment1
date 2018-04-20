package database.factory;

import database.hibernate.GameDatabaseHibernate;
import database.hibernate.MatchDatabaseHibernate;
import database.hibernate.TournamentDatabaseHibernate;
import database.hibernate.UserDatabaseHibernate;
import database.interfaces.GameDatabase;
import database.interfaces.MatchDatabase;
import database.interfaces.TournamentDatabase;
import database.interfaces.UserDatabase;

class HibernateFactory implements Factory {

    private static GameDatabase gdb = null;
    private static MatchDatabase mdb = null;
    private static UserDatabase udb = null;
    private static TournamentDatabase tdb = null;

    @Override
    public GameDatabase getGameDatabase() {
        if(gdb == null) {
            gdb = new GameDatabaseHibernate();
        }
        return gdb;
    }

    @Override
    public MatchDatabase getMatchDatabase() {
        if(mdb == null) {
            mdb = new MatchDatabaseHibernate();
        }
        return mdb;
    }

    @Override
    public UserDatabase getUserDatabase() {
        if(udb == null) {
            udb = new UserDatabaseHibernate();
        }
        return udb;
    }

    @Override
    public TournamentDatabase getTournamentDatabase() {
        if(tdb == null) {
            tdb = new TournamentDatabaseHibernate();
        }
        return tdb;
    }
}
