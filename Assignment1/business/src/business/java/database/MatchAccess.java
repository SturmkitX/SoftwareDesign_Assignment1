package database;

import implementations.mysql.MatchDAOImplem;
import interfaces.MatchDAO;
import models.Match;
import models.Tournament;
import session.UserSession;

public class MatchAccess {

	private static MatchDAO dao = new MatchDAOImplem();
	
	private MatchAccess() {
		
	}
	
	public static void insertMatch(Match match) {
		dao.insertMatch(match);
	}
	
	public static void updateMatch(Match match) {
		dao.updateMatch(match);
	}
	
	public static void deleteMatch(Match match) {
		// first, remove the match from tournament list
		Tournament t = UserSession.getActiveTournament();
		t.getMatches().remove(match);
		
		dao.deleteMatch(match.getId());
		TournamentAccess.updateTournament(t);
		
	}
}
