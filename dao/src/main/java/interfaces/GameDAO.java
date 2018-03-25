package interfaces;

import models.Game;

public interface GameDAO {
	public Game findGame(int id);
	public Game findEmptyGame();
	public void insertGame(Game game);
	public void updateGame(Game game);
	public void deleteGame(int id);
}
