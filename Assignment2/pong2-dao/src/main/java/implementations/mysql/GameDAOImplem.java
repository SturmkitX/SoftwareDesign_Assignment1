package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import drivers.ConnDriver;
import interfaces.GameDAO;
import entities.Game;


public class GameDAOImplem implements GameDAO {

    private Connection conn = ConnDriver.getInstance();

    @Override
    public Game findGame(int id) {
        Game result = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Games WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if(!rs.next()) {
                stmt.close();
                return null;
            }

            int p1Score = rs.getInt("p1score");
            int p2Score = rs.getInt("p2score");

            result = new Game(id, p1Score, p2Score, null);

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void insertGame(Game game) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Games (p1score, p2score, match_id) " +
                    "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, game.getP1Score());
            stmt.setInt(2, game.getP2Score());
            stmt.setInt(3, game.getMatch().getId());

            stmt.executeUpdate();

            // update generated key
            ResultSet keys = stmt.getGeneratedKeys();
            if(keys.next()) {
                game.setId(keys.getInt(1));
            }

            keys.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateGame(Game game) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Games SET p1score = ?, p2score = ? " +
                    "WHERE id = ?");
            stmt.setInt(1, game.getP1Score());
            stmt.setInt(2, game.getP2Score());
            stmt.setInt(3, game.getId());

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteGame(Game game) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Games WHERE id = ?");
            stmt.setInt(1, game.getId());

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}