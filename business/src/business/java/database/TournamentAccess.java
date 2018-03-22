package database;

import java.util.List;

import implementations.mysql.TournamentDAOImplem;
import interfaces.TournamentDAO;
import models.Tournament;

public final class TournamentAccess {
	
	private static TournamentDAO dao = new TournamentDAOImplem();
	
	private TournamentAccess() {
		
	}
	
	public static List<Tournament> getAllTournaments() {
		return dao.findAll();
	}
	
	public static Tournament getTournamentByName(String name) {
		return dao.findTournamentByName(name);
	}
}
