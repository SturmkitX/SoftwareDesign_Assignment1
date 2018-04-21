package dtos;

import entities.Match;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.util.ArrayList;

public class MatchDTO {
    private IntegerProperty id;
    private StringProperty p1;
    private StringProperty p2;
    private StringProperty stage;
    private ListProperty games;
    private StringProperty tournament;

    public MatchDTO() {
        this.id = new SimpleIntegerProperty();
        this.p1 = new SimpleStringProperty();
        this.p2 = new SimpleStringProperty();
        this.stage = new SimpleStringProperty();
        this.games = new SimpleListProperty();
        this.tournament = new SimpleStringProperty();
    }

    public MatchDTO(Match m) {
        this.id = new SimpleIntegerProperty(m.getId());
        this.p1 = new SimpleStringProperty(m.getP1().getName());
        this.p2 = new SimpleStringProperty(m.getP2().getName());
        this.stage = new SimpleStringProperty(getStageString(m.getStage()));
        this.games = new SimpleListProperty(FXCollections.observableArrayList(new ArrayList<>(m.getGames())));
        this.tournament = new SimpleStringProperty(m.getTournament().getName());
    }

    private String getStageString(int stage) {
        String result = null;
        switch(stage) {
            case 0 : result = new String("Quarters"); break;
            case 1 : result = new String("Semifinals"); break;
            case 2 : result = new String("Finals"); break;
            default : result = new String("");
        }

        return result;
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

    public String getP1() {
        return p1.get();
    }

    public StringProperty p1Property() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1.set(p1);
    }

    public String getP2() {
        return p2.get();
    }

    public StringProperty p2Property() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2.set(p2);
    }

    public String getStage() {
        return stage.get();
    }

    public StringProperty stageProperty() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage.set(stage);
    }

    public Object getGames() {
        return games.get();
    }

    public ListProperty gamesProperty() {
        return games;
    }

    public void setGames(Object games) {
        this.games.set(games);
    }

    public String getTournament() {
        return tournament.get();
    }

    public StringProperty tournamentProperty() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament.set(tournament);
    }
}
