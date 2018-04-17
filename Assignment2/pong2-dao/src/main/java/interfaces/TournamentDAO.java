package interfaces;

import java.sql.SQLException;
import java.util.List;

import entities.Tournament;

public interface TournamentDAO {
    Tournament findTournament(int id) throws SQLException;
    List<Tournament> findAll(int offset, int limit) throws SQLException;
    Tournament findTournamentByName(String name) throws SQLException;
    void insertTournament(Tournament tournament) throws SQLException;
    void updateTournament(Tournament tournament) throws SQLException;
    void deleteTournament(int id) throws SQLException;
}