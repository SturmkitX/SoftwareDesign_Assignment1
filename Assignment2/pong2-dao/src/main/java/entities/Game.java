package entities;

import java.util.Objects;

public class Game {
    private int id;
    private int p1Score;
    private int p2Score;

    public Game() {

    }

    public Game(int id, int p1Score, int p2Score) {
        this.id = id;
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getP1Score() {
        return p1Score;
    }

    public void setP1Score(int p1Score) {
        this.p1Score = p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    public void setP2Score(int p2Score) {
        this.p2Score = p2Score;
    }
}