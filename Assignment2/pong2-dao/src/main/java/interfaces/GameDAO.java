package interfaces;

import entities.Game;

import java.sql.SQLException;
import java.util.List;

public interface GameDAO {
    Game findGame(int id) throws SQLException;
    List<Game> findGameByMatchId(int id) throws SQLException;
    void insertGame(Game game, int match_id) throws SQLException;
    void updateGame(Game game) throws SQLException;
    void deleteGame(int id) throws SQLException;
}