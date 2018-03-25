package database;

import implementations.mysql.MatchDAOImplem;
import interfaces.MatchDAO;
import models.Match;

public class MatchAccess {

	private static MatchDAO dao = new MatchDAOImplem();
	
	private MatchAccess() {
		
	}
	
	public static Match insertMatch(Match match) {
		dao.insertMatch(match);
		return dao.findMatchByTuple(match.getP1().getId(), match.getP2().getId(), match.getStage());
	}
}
