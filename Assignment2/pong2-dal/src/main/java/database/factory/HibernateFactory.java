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
    @Override
    public GameDatabase getGameDatabase() {
        return new GameDatabaseHibernate();
    }

    @Override
    public MatchDatabase getMatchDatabase() {
        return new MatchDatabaseHibernate();
    }

    @Override
    public UserDatabase getUserDatabase() {
        return new UserDatabaseHibernate();
    }

    @Override
    public TournamentDatabase getTournamentDatabase() {
        return new TournamentDatabaseHibernate();
    }
}
