package implementations.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.mysql.jdbc.Statement;

import drivers.ConnDriver;
import entities.Game;
import entities.Match;
import entities.User;
import interfaces.GameDAO;
import interfaces.MatchDAO;
import interfaces.UserDAO;

public class MatchDAOImplem implements MatchDAO {

    private Connection conn = ConnDriver.getInstance();

    public Match findMatch(int id) {
        Match result = null;
        GameDAO gameDAO = new GameDAOImplem();
        UserDAO userDAO = new UserDAOImplem();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Matches WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if(!rs.next()) {
                stmt.close();
                return null;
            }

            int stage = rs.getInt("stage");
            int p1Id = rs.getInt("player1_id");
            int p2Id = rs.getInt("player2_id");

            rs.close();
            stmt.close();

            // resolve sets
            User p1 = userDAO.findUserById(p1Id);
            User p2 = userDAO.findUserById(p2Id);
            Set<Game> g = new HashSet<>();
            stmt = conn.prepareStatement("SELECT id FROM Games WHERE match_id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                g.add(gameDAO.findGame(rs.getInt("id")));
            }

            rs.close();
            stmt.close();

            
            result = new Match(id, p1, p2, stage, g, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

    @Override
    public void insertMatch(Match match) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Matches (player1_id, player2_id, stage, tournament_id) " +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, match.getP1().getId());
            stmt.setInt(2, match.getP2().getId());
            stmt.setInt(3, match.getStage());
            stmt.setInt(4, match.getTournament().getId());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if(keys.next()) {
                match.setId(keys.getInt(1));
            }

            keys.close();
            stmt.close();
        } catch (SQLException e) {
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

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteMatch(Match match) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Matches WHERE id = ?");
            stmt.setInt(1, match.getId());
            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}