package database.dao;

import database.interfaces.GameDatabase;
import entities.Game;
import implementations.mysql.GameDAOImplem;
import interfaces.GameDAO;

public class GameDatabaseDAO implements GameDatabase {
    private static GameDAO dao = new GameDAOImplem();

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
