package interfaces;

import entities.Game;

public interface GameDAO {
    public Game findGame(int id);
    public void insertGame(Game game);
    public void updateGame(Game game);
    public void deleteGame(int id);
}