package dtos;

import entities.Match;
import entities.Tournament;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import util.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TournamentDTO {
    private IntegerProperty id;
    private StringProperty name;
    private FloatProperty fee;
    private StringProperty status;     // to be replaced with an ENUM
    private ListProperty<MatchDTO> matches;
    private StringProperty startDate;
    private ListProperty users;
    private Button enroll;



    public TournamentDTO() {
        this.id = new SimpleIntegerProperty();

        this.name = new SimpleStringProperty();
        this.fee = new SimpleFloatProperty();
        this.status = new SimpleStringProperty();
        this.matches = new SimpleListProperty<>();
        this.startDate = new SimpleStringProperty();
        this.users = new SimpleListProperty();
        this.enroll = new Button("Enroll");
    }

    public TournamentDTO(Tournament t) {
        this.id = new SimpleIntegerProperty(t.getId());
        this.name = new SimpleStringProperty(t.getName());
        this.fee = new SimpleFloatProperty(t.getFee());
        this.users = new SimpleListProperty(FXCollections.observableArrayList(new ArrayList<>(t.getUsers())));
        this.status = new SimpleStringProperty(getStatusString(t.getStatus()));
        this.matches = new SimpleListProperty<>(getMatchesDTOs(t.getMatches()));
        this.startDate = new SimpleStringProperty(t.getStartDate().toString());
        this.enroll = new Button("Enroll");

    }

    public Button getEnroll() {
        return enroll;
    }

    public void setEnroll(Button enroll) {
        this.enroll = enroll;
    }

    private ObservableList<MatchDTO> getMatchesDTOs(Set<Match> matches) {
        ObservableList<MatchDTO> result = FXCollections.observableArrayList(new ArrayList<MatchDTO>());
        for(Match m : matches) {
            result.add(new MatchDTO(m));
        }

        return result;
    }

    private String getStatusString(int status) {
        String result;
        switch(status) {
            case 0 : if(users.contains(UserSession.getLogged())) {
                result = new String("Enrolled");
            } else {
                result = new String("Upcoming");
            } break;
            case 1 : result = new String("Ongoing"); break;
            case 2 : result = new String("Finished"); break;
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public float getFee() {
        return fee.get();
    }

    public FloatProperty feeProperty() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee.set(fee);
    }

    public Object getMatches() {
        return matches.get();
    }

    public ListProperty matchesProperty() {
        return matches;
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public void setMatches(ObservableList<MatchDTO> matches) {
        this.matches.set(matches);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public StringProperty startDateProperty() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public Object getUsers() {
        return users.get();
    }

    public ListProperty usersProperty() {
        return users;
    }

    public void setUsers(Object users) {
        this.users.set(users);
    }

    public static ObservableList<TournamentDTO> getTournamentLists(Set<Tournament> tours) {
        List<TournamentDTO> result = new ArrayList<>();
        for(Tournament t : tours) {
            result.add(new TournamentDTO(t));
        }

        return FXCollections.observableArrayList(result);
    }

    public String toString() {
        return new String("Tournamed DTO ID: " + id.get());
    }
}