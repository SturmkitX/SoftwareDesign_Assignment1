package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import drivers.ConnDriver;
import interfaces.GameDAO;
import entities.Game;


public class GameDAOImplem implements GameDAO {

    private Connection conn = ConnDriver.getInstance();

    public Game findGame(int id) throws SQLException {
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
    }

    @Override
    public List<Game> findGameByMatchId(int id) throws SQLException {
        List<Game> games = new ArrayList<>();

        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Games WHERE match_id = ?");
        stmt.setInt(1, id);

        ResultSet results = stmt.executeQuery();

        while(results.next()) {
            int game_id = results.getInt("id");
            int p1Score = results.getInt("p1score");
            int p2Score = results.getInt("p2score");
            games.add(new Game(game_id, p1Score, p2Score));

        }

        results.close();
        stmt.close();

        return games;
    }

    public void insertGame(Game game, int match_id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Games (p1score, p2score, match_id) " +
                "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, game.getP1Score());
        stmt.setInt(2, game.getP2Score());
        stmt.setInt(3, match_id);

        stmt.executeUpdate();

        // update generated key
        ResultSet keys = stmt.getGeneratedKeys();
        if(keys.next()) {
            game.setId(keys.getInt(1));
        }
    }

    public void updateGame(Game game) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Games SET p1score = ?, p2score = ? " +
                "WHERE id = ?");
        stmt.setInt(1, game.getP1Score());
        stmt.setInt(2, game.getP2Score());
        stmt.setInt(3, game.getId());

        stmt.executeUpdate();
    }

    public void deleteGame(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Games WHERE id = ?");
        stmt.setInt(1, id);

        stmt.executeUpdate();
    }

}