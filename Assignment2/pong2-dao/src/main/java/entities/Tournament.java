package entities;

import java.sql.Date;
import java.util.List;

public class Tournament {
    private int id;
    private String name;
    private float fee;
    private int status;     // to be replaced with an ENUM
    private List<Match> matches;
    private Date startDate;

    public Tournament(int id, String name, List<Match> matches, float fee, int status, Date startDate) {
        this.id = id;
        this.name = name;
        this.matches = matches;
        this.fee = fee;
        this.status = status;
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getFee() {
        return fee;
    }

    public int getStatus() {
        return status;
    }

    public Date getDate() {
        return startDate;
    }
}