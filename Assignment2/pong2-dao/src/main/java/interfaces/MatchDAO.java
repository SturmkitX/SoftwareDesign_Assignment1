package interfaces;

import entities.Match;

import java.sql.SQLException;

public interface MatchDAO {
    Match findMatch(int id) throws SQLException;
    void insertMatch(Match match, int tournament_id) throws SQLException;
    void updateMatch(Match match) throws SQLException;
    void deleteMatch(int id) throws SQLException;
}