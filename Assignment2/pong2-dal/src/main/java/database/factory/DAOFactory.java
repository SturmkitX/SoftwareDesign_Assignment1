package database.factory;

import database.dao.GameDatabaseDAO;
import database.dao.MatchDatabaseDAO;
import database.dao.TournamentDatabaseDAO;
import database.dao.UserDatabaseDAO;
import database.interfaces.GameDatabase;
import database.interfaces.MatchDatabase;
import database.interfaces.TournamentDatabase;
import database.interfaces.UserDatabase;

class DAOFactory implements Factory {
    private static GameDatabase gdb = null;
    private static MatchDatabase mdb = null;
    private static UserDatabase udb = null;
    private static TournamentDatabase tdb = null;

    @Override
    public GameDatabase getGameDatabase() {
        if (gdb == null) {
            gdb = new GameDatabaseDAO();
        }
        return gdb;
    }

    @Override
    public MatchDatabase getMatchDatabase() {
        if (mdb == null) {
            mdb = new MatchDatabaseDAO();
        }
        return mdb;
    }

    @Override
    public UserDatabase getUserDatabase() {
        if (udb == null) {
            udb = new UserDatabaseDAO();
        }
        return udb;
    }

    @Override
    public TournamentDatabase getTournamentDatabase() {
        if (tdb == null) {
            tdb = new TournamentDatabaseDAO();
        }
        return tdb;
    }
}
