package database;

import java.util.List;

import implementations.mysql.TournamentDAOImplem;
import interfaces.TournamentDAO;
import models.Tournament;
import session.UserSession;

public final class TournamentAccess {
	
	private static TournamentDAO dao = new TournamentDAOImplem();
	
	private TournamentAccess() {
		
	}
	
	public static List<Tournament> getAllTournaments() {
		return dao.findAll(UserSession.getTournamentOffset(), UserSession.getTournamentLimit());
	}
	
	public static Tournament getTournamentByName(String name) {
		return dao.findTournamentByName(name);
	}
	
	public static Tournament getTournamentById(int id) {
		return dao.findTournament(id);
	}
	
	public static void updateTournament(Tournament tournament) {
		dao.updateTournament(tournament);
	}
	
	public static void deleteTournament(int id) {
		dao.deleteTournament(id);
	}
	
	public static void insertTournament(Tournament tournament) {
		dao.insertTournament(tournament);
	}
}
