package entities;

import java.util.List;

public class Match {
    private int id;
    private User p1;
    private User p2;
    private int stage;
    private List<Game> games;

    public Match(int id, User p1, User p2, int stage, List<Game> games) {
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

    public int getStage() {
        return stage;
    }

    public List<Game> getGames() {
        return games;
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