package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import drivers.ConnDriver;
import interfaces.GameDAO;
import interfaces.MatchDAO;
import interfaces.UserDAO;
import models.Game;
import models.Match;
import models.User;

public class MatchDAOImplem implements MatchDAO {
	
	Connection conn = ConnDriver.getInstance();

	public Match findMatch(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Matches WHERE id = ?");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT game_id FROM GameMatch " + 
					"WHERE match_id = ?");
			stmt.setInt(1, id);
			stmt2.setInt(1, id);
			
			ResultSet results = stmt.executeQuery();
			ResultSet resultsGame = stmt2.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			
			UserDAO userDao = new UserDAOImplem();
			GameDAO gameDao = new GameDAOImplem();
			User u1 = userDao.findUserById(results.getInt("player1_id"));
			User u2 = userDao.findUserById(results.getInt("player2_id"));
			int stage = results.getInt("stage");
			List<Game> games = new ArrayList<Game>();
			
			while(resultsGame.next()) {
				Game g = gameDao.findGame(resultsGame.getInt("game_id"));
				games.add(g);
			}
			
			results.close();
			resultsGame.close();
			
			return new Match(id, u1, u2, stage, games);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void insertMatch(Match match) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Matches (player1_id, player2_id, stage) " +
					"VALUES (?, ?, ?)");
			PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO GameMatch (game_id, match_id) " + 
					"VALUES (?, ?)");
			stmt.setInt(1, match.getP1().getId());
			stmt.setInt(2, match.getP2().getId());
			stmt.setInt(3, match.getStage());
			
			stmt.executeUpdate();
			
			for(Game game : match.getGames()) {
				stmt2.setInt(1, game.getId());
				stmt2.setInt(2, match.getId());
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void updateMatch(Match match) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE Matches SET player1_id = ?, player2_id = ?, " + 
					"stage = ? WHERE id = ?");
			stmt.setInt(1, match.getP1().getId());
			stmt.setInt(2, match.getP2().getId());
			stmt.setInt(3, match.getStage());
			stmt.setInt(4, match.getId());
			
			PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM GameMatch WHERE match_id = ?");
			stmt2.setInt(1, match.getId());
			
			stmt.executeUpdate();
			stmt2.executeUpdate();
			
			PreparedStatement stmt3 = conn.prepareStatement("INSERT INTO GameMatch (game_id, match_id) " + 
					"VALUES (?, ?)");
			for(Game game : match.getGames()) {
				stmt3.setInt(1, game.getId());
				stmt3.setInt(2, match.getId());
				stmt3.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteMatch(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Matches WHERE id = ?");
			PreparedStatement stmt2 = conn.prepareStatement("DELETE FROM GameMatch WHERE match_id = ?");
			
			stmt.setInt(1, id);
			stmt2.setInt(1, id);
			
			stmt.executeUpdate();
			stmt2.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Match findMatchByTuple(int p1Id, int p2Id, int stage) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Matches WHERE " +
					"player1_id = ? AND player2_id = ? AND stage = ?");
			PreparedStatement stmt2 = conn.prepareStatement("SELECT game_id FROM GameMatch " + 
					"WHERE match_id = ?");
			stmt.setInt(1, p1Id);
			stmt.setInt(2, p2Id);
			stmt.setInt(3, stage);
			
			ResultSet results = stmt.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			
			UserDAO userDao = new UserDAOImplem();
			GameDAO gameDao = new GameDAOImplem();
			User u1 = userDao.findUserById(p1Id);
			User u2 = userDao.findUserById(p2Id);
			int match_id = results.getInt("id");
			List<Game> games = new ArrayList<Game>();
			
			stmt2.setInt(1, match_id);
			ResultSet resultsGame = stmt2.executeQuery();
			
			while(resultsGame.next()) {
				Game g = gameDao.findGame(resultsGame.getInt("game_id"));
				games.add(g);
			}
			
			results.close();
			resultsGame.close();
			
			return new Match(match_id, u1, u2, stage, games);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
