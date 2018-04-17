package entities;

import java.sql.Date;
import java.util.Set;

public class Tournament {
    private int id;
    private String name;
    private float fee;
    private int status;     // to be replaced with an ENUM
    private Set<Match> matches;
    private Date startDate;

    public Tournament() {

    }

    public Tournament(int id, String name, Set<Match> matches, float fee, int status, Date startDate) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<Match> getMatches() {
        return matches;
    }

    public void setMatches(Set<Match> matches) {
        this.matches = matches;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}