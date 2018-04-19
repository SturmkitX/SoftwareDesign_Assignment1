package database.interfaces;

import entities.Tournament;

import java.util.Set;

public interface TournamentDatabase {
    Tournament findTournament(int id);
    Set<Tournament> findAll();
    Tournament findTournamentByName(String name);
    void insertTournament(Tournament tournament);
    void updateTournament(Tournament tournament);
    void deleteTournament(Tournament tournament);
}
