package entities;

import java.util.Objects;
import java.util.Set;

public class Match {
    private int id;
    private User p1;
    private User p2;
    private int stage;
    private Set<Game> games;

    public Match() {

    }

    public Match(int id, User p1, User p2, int stage, Set<Game> games) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.stage = stage;
        this.games = games;
    }

    public int getId() {
        return id;
    }

    public User getP1() {
        return p1;
    }

    public User getP2() {
        return p2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return id == match.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public int getStage() {
        return stage;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setP1(User p) {
        this.p1 = p;
    }

    public void setP2(User p) {
        this.p2 = p;
    }
}