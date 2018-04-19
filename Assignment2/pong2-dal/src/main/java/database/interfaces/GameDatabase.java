package database.interfaces;

import entities.Game;

public interface GameDatabase {
    Game findGame(int id);
    void insertGame(Game game);
    void updateGame(Game game);
    void deleteGame(Game game);
}
