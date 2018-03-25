package logic;

import models.Game;
import models.Match;

/*
 * Matches are played best 3 out of 5
 */

public class MatchLogic {

	private MatchLogic() {
		
	}
	
	public static int getWinner(Match match) {
		if(match.getGames().size() < 3) {
			return 0;
		}
		
		int total = 0;
		for(Game g : match.getGames()) {
			int partResult = GameLogic.getWinner(g);
			switch(partResult) {
			case 1 : total++; break;
			case 2 : total--; break;
			default : ;	// nothing
			}
		}
		
		if(total == 0) {	// tie; match must continue
			return 0;
		}
		
		return ((total > 0) ? 1 : 2);
	}
}
