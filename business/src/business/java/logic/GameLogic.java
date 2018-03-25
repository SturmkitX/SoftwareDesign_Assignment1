package logic;

import models.Game;
/*
 * Winners:
 * 0 = none
 * 1 = player 1
 * 2 = player 2
 */

public class GameLogic {
	
	private GameLogic() {
		
	}
	
	public static int getWinner(Game game) {
		if(game.getP1Score() < 11 && game.getP2Score() < 11) {
			return 0;
		}
		
		int scoreDiff = Math.abs(game.getP1Score() - game.getP2Score());
		
		if(game.getP1Score() >= 11 && scoreDiff >= 2) {
			return 1;
		}
		
		if(game.getP2Score() >= 11 && scoreDiff >= 2) {
			return 2;
		}
		
		return 0;
	}
}
