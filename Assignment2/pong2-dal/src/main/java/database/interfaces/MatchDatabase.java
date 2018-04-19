package database.interfaces;

import entities.Match;

public interface MatchDatabase {
    Match findMatch(int id);
    void insertMatch(Match match);
    void updateMatch(Match match);
    void deleteMatch(Match match);
}
