package dtos;


import entities.Game;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import util.RuntimeUtil;

public class GameDTO {
    private Game source;
    private IntegerProperty id;
    private IntegerProperty p1Score;
    private IntegerProperty p2Score;
    private StringProperty p1Status;
    private StringProperty p2Status;

    public GameDTO(Game game) {
        this.source = game;
        this.id = new SimpleIntegerProperty(game.getId());
        this.p1Score = new SimpleIntegerProperty(game.getP1Score());
        this.p2Score = new SimpleIntegerProperty(game.getP2Score());

        int winner = RuntimeUtil.getWinner(game);
        if(winner == 1) {
            p1Status = new SimpleStringProperty("Winner");
            p2Status = new SimpleStringProperty();
        } else if(winner == 2) {
            p1Status = new SimpleStringProperty();
            p2Status = new SimpleStringProperty("Winner");
        } else {
            p1Status = new SimpleStringProperty();
            p2Status = new SimpleStringProperty();
        }
    }

    public String getP1Status() {
        return p1Status.get();
    }

    public StringProperty p1StatusProperty() {
        return p1Status;
    }

    public void setP1Status(String p1Status) {
        this.p1Status.set(p1Status);
    }

    public String getP2Status() {
        return p2Status.get();
    }

    public StringProperty p2StatusProperty() {
        return p2Status;
    }

    public void setP2Status(String p2Status) {
        this.p2Status.set(p2Status);
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
