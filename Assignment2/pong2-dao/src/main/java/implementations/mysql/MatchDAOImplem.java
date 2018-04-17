package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import drivers.ConnDriver;
import entities.Match;
import entities.User;
import interfaces.MatchDAO;
import interfaces.UserDAO;

public class MatchDAOImplem implements MatchDAO {

    private Connection conn = ConnDriver.getInstance();

    public Match findMatch(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Matches WHERE id = ?");
        stmt.setInt(1, id);

        ResultSet results = stmt.executeQuery();
        // System.out.println(results);

        if(!results.isBeforeFirst()) {
            stmt.close();
            return null;
        }

        results.next();

        UserDAO userDao = new UserDAOImplem();
        User u1 = userDao.findUserById(results.getInt("player1_id"));
        User u2 = userDao.findUserById(results.getInt("player2_id"));
        int stage = results.getInt("stage");

        results.close();

        return new Match(id, u1, u2, stage, null, null);
    }

    public void insertMatch(Match match, int tournament_id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Matches (player1_id, player2_id, stage, tournament_id) " +
                "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, match.getP1().getId());
        stmt.setInt(2, match.getP2().getId());
        stmt.setInt(3, match.getStage());
        stmt.setInt(4, tournament_id);

        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if(keys.next()) {
            match.setId(keys.getInt(1));
        }

    }

    public void updateMatch(Match match) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Matches SET player1_id = ?, player2_id = ?, " +
                "stage = ? WHERE id = ?");
        stmt.setInt(1, match.getP1().getId());
        stmt.setInt(2, match.getP2().getId());
        stmt.setInt(3, match.getStage());
        stmt.setInt(4, match.getId());

        stmt.executeUpdate();
    }

    public void deleteMatch(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Matches WHERE id = ?");
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }

}