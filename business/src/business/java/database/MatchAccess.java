package database;

import implementations.mysql.MatchDAOImplem;
import interfaces.MatchDAO;
import models.Match;

public class MatchAccess {

	private static MatchDAO dao = new MatchDAOImplem();
	
	private MatchAccess() {
		
	}
	
	public static void insertMatch(Match match) {
		dao.insertMatch(match);
	}
}
