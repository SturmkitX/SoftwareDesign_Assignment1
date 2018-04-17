package implementations.mysql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import drivers.ConnDriver;
import entities.Tournament;
import interfaces.TournamentDAO;

public class TournamentDAOImplem implements TournamentDAO {

    private Connection conn = ConnDriver.getInstance();

    public Tournament findTournament(int id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments WHERE id = ?");
        stmt.setInt(1, id);

        ResultSet results = stmt.executeQuery();
        // System.out.println(results);

        if(!results.isBeforeFirst()) {
            stmt.close();
            return null;
        }

        results.next();

        String name = results.getString("name");
        float fee = results.getFloat("fee");
        int status = results.getInt("status");
        Date date = results.getDate("start_date");

        results.close();

        return new Tournament(id, name, null, fee, status, date);
    }

    public void insertTournament(Tournament tournament) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tournaments (name, fee, status, start_date) " +
                "VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, tournament.getName());
        stmt.setFloat(2, tournament.getFee());
        stmt.setInt(3, tournament.getStatus());
        stmt.setDate(4, tournament.getDate());

        stmt.executeUpdate();

        ResultSet keys = stmt.getGeneratedKeys();
        if(keys.next()) {
            tournament.setId(keys.getInt(1));
        }

    }

    public void updateTournament(Tournament tournament) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("UPDATE Tournaments SET name = ?, fee = ?, status = ?, start_date = ? " +
                "WHERE id = ?");
        stmt.setString(1, tournament.getName());
        stmt.setFloat(2, tournament.getFee());
        stmt.setInt(3, tournament.getStatus());
        stmt.setDate(4, tournament.getDate());
        stmt.setInt(5, tournament.getId());

        stmt.executeUpdate();

    }

    public void deleteTournament(int id) throws SQLException {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tournaments WHERE id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();

    }

    public List<Tournament> findAll(int offset, int limit) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments LIMIT ? OFFSET ?");

        stmt.setInt(1, limit);
        stmt.setInt(2, offset);
        ResultSet results = stmt.executeQuery();

        List<Tournament> tournaments = new ArrayList<>();

        while(results.next()) {
            String name = results.getString("name");
            int id = results.getInt("id");
            float fee = results.getFloat("fee");
            int status = results.getInt("status");
            Date date = results.getDate("start_date");

            tournaments.add(new Tournament(id, name, null, fee, status, date));
        }

        results.close();

        return tournaments;
    }

    public Tournament findTournamentByName(String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Tournaments WHERE name = ?");
        stmt.setString(1, name);


        ResultSet results = stmt.executeQuery();

        // System.out.println(results);

        if(!results.isBeforeFirst()) {
            stmt.close();
            return null;
        }

        results.next();

        int id = results.getInt("id");
        float fee = results.getFloat("fee");
        int status = results.getInt("status");
        Date date = results.getDate("start_date");

        results.close();

        return new Tournament(id, name, null, fee, status, date);
    }

}