package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import drivers.ConnDriver;
import interfaces.MatchDAO;
import interfaces.TournamentDAO;
import models.Match;
import models.Tournament;

public class TournamentImplem implements TournamentDAO {
	
	Connection conn = ConnDriver.getInstance();

	public Tournament findTournament(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments WHERE id = ?");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT game_id FROM MatchTournament " + 
					"WHERE tournament_id = ?");
			stmt.setInt(1, id);
			stmt2.setInt(1, id);
			
			ResultSet results = stmt.executeQuery();
			ResultSet resultsMatch = stmt2.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			
			MatchDAO matchDao = new MatchDAOImplem();
			String name = results.getString("name");
			List<Match> matches = new ArrayList<Match>();
			
			while(resultsMatch.next()) {
				Match m = matchDao.findMatch(resultsMatch.getInt("match_id"));
				matches.add(m);
			}
			
			results.close();
			resultsMatch.close();
			
			return new Tournament(id, name, matches);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void insertTournament(Tournament tournament) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tournaments (name) " +
					"VALUES (?)");
			PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO MatchTournament (match_id, tournament_id) " + 
					"VALUES (?, ?)");
			stmt.setString(1, tournament.getName());
			
			stmt.executeUpdate();
			
			for(Match match : tournament.getMatches()) {
				stmt2.setInt(1, match.getId());
				stmt2.setInt(2, tournament.getId());
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateTournament(Tournament tournament) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE Tournaments SET name = ? " + 
					"WHERE id = ?");
			stmt.setString(1, tournament.getName());
			stmt.setInt(2, tournament.getId());
			
			PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM MatchTournament WHERE tournament_id = ?");
			stmt2.setInt(1, tournament.getId());
			
			stmt.executeUpdate();
			stmt2.executeUpdate();
			
			PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO MatchTournament (match_id, tournament_id) " + 
					"VALUES (?, ?)");
			for(Match match : tournament.getMatches()) {
				stmt3.setInt(1, match.getId());
				stmt3.setInt(2, tournament.getId());
				stmt3.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void deleteTournament(int id) {
		// TODO Auto-generated method stub
		
	}

}
