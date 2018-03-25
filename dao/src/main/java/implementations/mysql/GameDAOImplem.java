package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import drivers.ConnDriver;
import interfaces.GameDAO;
import models.Game;

public class GameDAOImplem implements GameDAO {
	
	Connection conn = ConnDriver.getInstance();

	public Game findGame(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Games WHERE id = ?");
			stmt.setInt(1, id);
			
			ResultSet results = stmt.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			int p1Score = results.getInt("p1score");
			int p2Score = results.getInt("p2score");
			
			results.close();
			return new Game(id, p1Score, p2Score);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void insertGame(Game game) {
		try {
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO Games (p1score, p2score) " +
					"VALUES (?, ?)");
			stmt.setInt(1, game.getP1Score());
			stmt.setInt(2, game.getP2Score());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateGame(Game game) {
		try {
			PreparedStatement stmt = conn.prepareStatement("UPDATE Games SET p1score = ?, p2score = ? " + 
					"WHERE id = ?");
			stmt.setInt(1, game.getP1Score());
			stmt.setInt(2, game.getP2Score());
			stmt.setInt(3, game.getId());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteGame(int id) {
		try {
			PreparedStatement stmt = conn.prepareStatement("DELETE FROM Games WHERE id = ?");
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Game findEmptyGame() {
		try {
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Games WHERE id = ?");
			stmt.setInt(1, id);
			
			ResultSet results = stmt.executeQuery();
			// System.out.println(results);
			
			if(!results.isBeforeFirst()) {
				stmt.close();
				return null;
			}
			
			results.next();
			int p1Score = results.getInt("p1score");
			int p2Score = results.getInt("p2score");
			
			results.close();
			return new Game(id, p1Score, p2Score);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
