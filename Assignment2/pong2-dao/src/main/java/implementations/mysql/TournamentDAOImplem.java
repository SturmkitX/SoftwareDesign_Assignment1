package implementations.mysql;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

import drivers.ConnDriver;
import entities.Match;
import entities.Tournament;
import entities.User;
import interfaces.MatchDAO;
import interfaces.TournamentDAO;
import interfaces.UserDAO;

public class TournamentDAOImplem implements TournamentDAO {

    private Connection conn = ConnDriver.getInstance();

    @Override
    public Tournament findTournament(int id) {
        Tournament result = null;
        MatchDAO matchDAO = new MatchDAOImplem();
        UserDAO userDAO = new UserDAOImplem();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments WHERE id = ?");
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            // System.out.println(results);

            if(!rs.next()) {
                stmt.close();
                return null;
            }

            String name = rs.getString("name");
            float fee = rs.getFloat("fee");
            int status = rs.getInt("status");
            Date date = rs.getDate("start_date");

            rs.close();
            stmt.close();
            
            // resolve sets
            Set<Match> m = new HashSet<>();
            Set<User> u = new HashSet<>();
            
            stmt = conn.prepareStatement("SELECT id FROM Matches WHERE tournament_id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                m.add(matchDAO.findMatch(rs.getInt("id")));
            }
            rs.close();
            stmt.close();
            
            stmt = conn.prepareStatement("SELECT id_user FROM UserTournament WHERE id_tournament = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                u.add(userDAO.findUserById(rs.getInt("id_user")));
            }
            rs.close();
            stmt.close();

            result = new Tournament(id, name, fee, status, m, date, u);

            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public Set<Tournament> findAll() {
        Set<Tournament> result = null;
        MatchDAO matchDAO = new MatchDAOImplem();
        UserDAO userDAO = new UserDAOImplem();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments");
            ResultSet rs = stmt.executeQuery();

            result = new HashSet<>();

            while(rs.next()) {
                String name = rs.getString("name");
                int id = rs.getInt("id");
                float fee = rs.getFloat("fee");
                int status = rs.getInt("status");
                Date date = rs.getDate("start_date");

                // resolve sets
                Set<Match> m = new HashSet<>();
                Set<User> u = new HashSet<>();

                PreparedStatement stmt2 = conn.prepareStatement("SELECT id FROM Matches WHERE tournament_id = ?");
                stmt2.setInt(1, id);
                ResultSet rs2 = stmt2.executeQuery();
                while(rs2.next()) {
                    m.add(matchDAO.findMatch(rs2.getInt("id")));
                }
                rs2.close();
                stmt2.close();

                stmt2 = conn.prepareStatement("SELECT id_user FROM UserTournament WHERE id_tournament = ?");
                stmt2.setInt(1, id);
                rs2 = stmt2.executeQuery();
                while(rs2.next()) {
                    u.add(userDAO.findUserById(rs2.getInt("id_user")));
                }
                rs2.close();
                stmt2.close();

                result.add(new Tournament(id, name, fee, status, m, date, u));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void insertTournament(Tournament tournament) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tournaments (name, fee, status, start_date) " +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tournament.getName());
            stmt.setFloat(2, tournament.getFee());
            stmt.setInt(3, tournament.getStatus());
            stmt.setDate(4, tournament.getStartDate());

            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if(keys.next()) {
                tournament.setId(keys.getInt(1));
            }

            keys.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateTournament(Tournament tournament) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Tournaments SET name = ?, fee = ?, status = ?, start_date = ? " +
                    "WHERE id = ?");
            stmt.setString(1, tournament.getName());
            stmt.setFloat(2, tournament.getFee());
            stmt.setInt(3, tournament.getStatus());
            stmt.setDate(4, tournament.getStartDate());
            stmt.setInt(5, tournament.getId());

            stmt.executeUpdate();

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteTournament(Tournament tournament) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tournaments WHERE id = ?");
            stmt.setInt(1, tournament.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Tournament findTournamentByName(String name) {
        Tournament result = null;
        MatchDAO matchDAO = new MatchDAOImplem();
        UserDAO userDAO = new UserDAOImplem();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments WHERE name = ?");
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            // System.out.println(results);

            if(!rs.next()) {
                stmt.close();
                return null;
            }

            int id = rs.getInt("id");
            float fee = rs.getFloat("fee");
            int status = rs.getInt("status");
            Date date = rs.getDate("start_date");

            // resolve sets
            Set<Match> m = new HashSet<>();
            Set<User> u = new HashSet<>();

            stmt = conn.prepareStatement("SELECT id FROM Matches WHERE tournament_id = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                m.add(matchDAO.findMatch(rs.getInt("id")));
            }
            rs.close();
            stmt.close();

            stmt = conn.prepareStatement("SELECT id_user FROM UserTournament WHERE id_tournament = ?");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while(rs.next()) {
                u.add(userDAO.findUserById(rs.getInt("id_user")));
            }
            rs.close();
            stmt.close();

            result = new Tournament(id, name, fee, status, m, date, u);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

}