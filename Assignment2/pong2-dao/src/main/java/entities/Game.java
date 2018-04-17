package entities;

public class Game {
    private int id;
    private int p1Score;
    private int p2Score;

    public Game(int id, int p1Score, int p2Score) {
        this.id = id;
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    public int getId() {
        return id;
    }

    public int getP1Score() {
        return p1Score;
    }

    public int getP2Score() {
        return p2Score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setP1Score(int score) {
        this.p1Score = score;
    }

    public void setP2Score(int score) {
        this.p2Score = score;
    }
}