package database.factory;

import database.interfaces.GameDatabase;
import database.interfaces.MatchDatabase;
import database.interfaces.TournamentDatabase;
import database.interfaces.UserDatabase;

public interface Factory {
    GameDatabase getGameDatabase();
    MatchDatabase getMatchDatabase();
    UserDatabase getUserDatabase();
    TournamentDatabase getTournamentDatabase();
}
