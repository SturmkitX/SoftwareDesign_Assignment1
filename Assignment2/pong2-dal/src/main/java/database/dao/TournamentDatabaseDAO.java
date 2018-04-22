package database.dao;

import database.interfaces.TournamentDatabase;
import entities.Match;
import entities.Tournament;
import entities.User;
import implementations.mysql.TournamentDAOImplem;
import interfaces.TournamentDAO;

import java.util.Set;

public class TournamentDatabaseDAO implements TournamentDatabase {

    private static TournamentDAO dao = new TournamentDAOImplem();

    @Override
    public Tournament findTournament(int id) {
        Tournament t = dao.findTournament(id);
        for(Match m : t.getMatches()) {
            m.setTournament(t);
        }

        for(User u : t.getUsers()) {
            u.getTournaments().add(t);
        }

        return t;
    }

    @Override
    public Set<Tournament> findAll() {
        Set<Tournament> res = dao.findAll();
        for(Tournament t : res) {
            for(Match m : t.getMatches()) {
                m.setTournament(t);
            }

            for(User u : t.getUsers()) {
                u.getTournaments().add(t);
            }
        }

        return res;
    }

    @Override
    public Tournament findTournamentByName(String name) {
        Tournament t = dao.findTournamentByName(name);
        for(Match m : t.getMatches()) {
            m.setTournament(t);
        }

        for(User u : t.getUsers()) {
            u.getTournaments().add(t);
        }

        return t;
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
