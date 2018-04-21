package dtos;


import entities.Game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameDTO {
    private Game source;
    private IntegerProperty id;
    private IntegerProperty p1Score;
    private IntegerProperty p2Score;

    public GameDTO(Game game) {
        this.source = game;
        this.id = new SimpleIntegerProperty(game.getId());
        this.p1Score = new SimpleIntegerProperty(game.getP1Score());
        this.p2Score = new SimpleIntegerProperty(game.getP2Score());
    }

    public Game getSource() {
        return source;
    }

    public void setSource(Game source) {
        this.source = source;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getP1Score() {
        return p1Score.get();
    }

    public IntegerProperty p1ScoreProperty() {
        return p1Score;
    }

    public void setP1Score(int p1Score) {
        this.p1Score.set(p1Score);
    }

    public int getP2Score() {
        return p2Score.get();
    }

    public IntegerProperty p2ScoreProperty() {
        return p2Score;
    }

    public void setP2Score(int p2Score) {
        this.p2Score.set(p2Score);
    }
}
