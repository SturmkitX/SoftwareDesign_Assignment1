package interfaces;

import java.util.List;

import models.Tournament;

public interface TournamentDAO {
	public Tournament findTournament(int id);
	public List<Tournament> findAll();
	public void insertTournament(Tournament tournament);
	public void updateTournament(Tournament tournament);
	public void deleteTournament(int id);
}
