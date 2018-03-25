package database;

import implementations.mysql.GameDAOImplem;
import interfaces.GameDAO;
import models.Game;

public class GameAccess {
	
	private static GameDAO dao = new GameDAOImplem();
	
	private GameAccess() {
		
	}
	
	public static void insertGame(Game game) {
		dao.insertGame(game);
	}
}
