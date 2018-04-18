package interfaces;

import entities.Game;

import java.util.Set;

public interface GameDAO {
    Game findGame(int id);
    Set<Game> findGameByMatchId(int id);
    void insertGame(Game game);
    void updateGame(Game game);
    void deleteGame(Game game);
}