package database.dao;

import database.interfaces.TournamentDatabase;
import entities.Tournament;
import implementations.mysql.TournamentDAOImplem;
import interfaces.TournamentDAO;

import java.util.Set;

public class TournamentDatabaseDAO implements TournamentDatabase {

    private static TournamentDAO dao = new TournamentDAOImplem();

    @Override
    public Tournament findTournament(int id) {
        return dao.findTournament(id);
    }

    @Override
    public Set<Tournament> findAll() {
        return dao.findAll();
    }

    @Override
    public Tournament findTournamentByName(String name) {
        return dao.findTournamentByName(name);
    }

    @Override
    public void insertTournament(Tournament tournament) {
        dao.insertTournament(tournament);
    }

    @Override
    public void updateTournament(Tournament tournament) {
        dao.updateTournament(tournament);
    }

    @Override
    public void deleteTournament(Tournament tournament) {
        dao.deleteTournament(tournament);
    }
}
