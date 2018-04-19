package database.hibernate;

import database.interfaces.GameDatabase;
import entities.Game;
import implementations.hibernate.GameHibernate;
import interfaces.GameDAO;

public class GameDatabaseHibernate implements GameDatabase {

    private static GameDAO dao = new GameHibernate();

    @Override
    public Game findGame(int id) {
        return dao.findGame(id);
    }

    @Override
    public void insertGame(Game game) {
        dao.insertGame(game);
    }

    @Override
    public void updateGame(Game game) {
        dao.updateGame(game);
    }

    @Override
    public void deleteGame(Game game) {
        dao.deleteGame(game);
    }
}
