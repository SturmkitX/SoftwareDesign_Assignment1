package dtos;

import entities.Match;
import entities.Tournament;
import entities.User;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashSet;
import java.util.Set;

public class UserDTO {
    private IntegerProperty id;
    private StringProperty email;
    private StringProperty password;
    private StringProperty name;
    private BooleanProperty admin;
    private StringProperty balance;
    private ListProperty<MatchDTO> matchesP1;
    private ListProperty<MatchDTO> matchesP2;
    private ListProperty<TournamentDTO> tournaments;

    public UserDTO() {
        this.id = new SimpleIntegerProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.admin = new SimpleBooleanProperty();
        this.balance = new SimpleStringProperty();
        this.matchesP1 = new SimpleListProperty<>();
        this.matchesP2 = new SimpleListProperty<>();
        this.tournaments = new SimpleListProperty<>();
    }

    public UserDTO(User user) {
        this.id = new SimpleIntegerProperty(user.getId());
        this.email = new SimpleStringProperty(user.getEmail());
        this.password = new SimpleStringProperty(user.getPassword());
        this.name = new SimpleStringProperty(user.getName());
        this.admin = new SimpleBooleanProperty(user.isAdmin());
        this.balance = new SimpleStringProperty("" + user.getBalance());
        this.matchesP1 = new SimpleListProperty<>(getMatchesList(user.getMatchesP1()));
        this.matchesP2 = new SimpleListProperty<>(getMatchesList(user.getMatchesP2()));
        this.tournaments = new SimpleListProperty<>(getTournamentsList(user.getTournaments()));
    }

    private ObservableList<MatchDTO> getMatchesList(Set<Match> matches) {
        ObservableList<MatchDTO> result = FXCollections.observableArrayList();
        for(Match m : matches) {
            result.add(new MatchDTO(m));
        }

        return result;
    }

    private ObservableList<TournamentDTO> getTournamentsList(Set<Tournament> tournaments) {
        ObservableList<TournamentDTO> result = FXCollections.observableArrayList();
        for(Tournament t : tournaments) {
            result.add(new TournamentDTO(t));
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

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public boolean isAdmin() {
        return admin.get();
    }

    public BooleanProperty adminProperty() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin.set(admin);
    }

    public String getBalance() {
        return balance.get();
    }

    public StringProperty balanceProperty() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance.set(balance);
    }

    public ObservableList<MatchDTO> getMatchesP1() {
        return matchesP1.get();
    }

    public ListProperty<MatchDTO> matchesP1Property() {
        return matchesP1;
    }

    public void setMatchesP1(ObservableList<MatchDTO> matchesP1) {
        this.matchesP1.set(matchesP1);
    }

    public ObservableList<MatchDTO> getMatchesP2() {
        return matchesP2.get();
    }

    public ListProperty<MatchDTO> matchesP2Property() {
        return matchesP2;
    }

    public void setMatchesP2(ObservableList<MatchDTO> matchesP2) {
        this.matchesP2.set(matchesP2);
    }

    public ObservableList<TournamentDTO> getTournaments() {
        return tournaments.get();
    }

    public ListProperty<TournamentDTO> tournamentsProperty() {
        return tournaments;
    }

    public void setTournaments(ObservableList<TournamentDTO> tournaments) {
        this.tournaments.set(tournaments);
    }

    public static User toUser(UserDTO dto) {
        Set<Match> m1 = new HashSet<>();
        Set<Match> m2 = new HashSet<>();
        Set<Tournament> tour = new HashSet<>();

        for(MatchDTO m : dto.getMatchesP1()) {
            m1.add(MatchDTO.toMatch(m));
        }

        for(MatchDTO m : dto.getMatchesP2()) {
            m2.add(MatchDTO.toMatch(m));
        }

        for(TournamentDTO t : dto.getTournaments()) {
            tour.add(MatchDTO.toTournament(t));
        }

        return new User(dto.getId(), dto.getEmail(), dto.getPassword(), dto.getName(), dto.isAdmin(), Float.parseFloat(dto.getBalance()),
                m1, m2, tour);
    }
}
