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
		
		int p1Points = 0;
		int p2Points = 0;
		for(Game g : match.getGames()) {
			int partResult = GameLogic.getWinner(g);
			switch(partResult) {
			case 1 : p1Points++; break;
			case 2 : p2Points++; break;
			default : ;	// nothing
			}
		}
		
		if(p1Points >= 3) {
			return 1;
		}
		
		if(p2Points >= 3) {
			return 2;
		}
		
		return 0;
	}
}
