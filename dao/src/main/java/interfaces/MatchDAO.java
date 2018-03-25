package interfaces;

import models.Match;

public interface MatchDAO {
	public Match findMatch(int id);
	public Match findMatchByTuple(int p1Id, int p2Id, int stage);
	public void insertMatch(Match match);
	public void updateMatch(Match match);
	public void deleteMatch(int id);
}
