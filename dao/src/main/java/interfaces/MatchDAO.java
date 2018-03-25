package interfaces;

import models.Match;

public interface MatchDAO {
	public Match findMatch(int id);
	public void insertMatch(Match match);
	public void updateMatch(Match match);
	public void deleteMatch(int id);
}
