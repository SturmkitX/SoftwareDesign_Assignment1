package database.factory;

import database.interfaces.GameDatabase;
import database.interfaces.MatchDatabase;
import database.interfaces.TournamentDatabase;
import database.interfaces.UserDatabase;

class DAOFactory implements Factory {
    @Override
    public GameDatabase getGameDatabase() {
        return null;
    }

    @Override
    public MatchDatabase getMatchDatabase() {
        return null;
    }

    @Override
    public UserDatabase getUserDatabase() {
        return null;
    }

    @Override
    public TournamentDatabase getTournamentDatabase() {
        return null;
    }
}
