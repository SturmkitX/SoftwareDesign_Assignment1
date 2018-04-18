package entities;

import java.util.Set;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private boolean admin;
    private float balance;
    private Set<Match> matchesP1;
    private Set<Match> matchesP2;
    private Set<Tournament> tournaments;

    public User() {

    }

    public User(int id, String email, String password, String name, boolean admin, float balance, Set<Match> matchesP1, Set<Match> matchesP2, Set<Tournament> tournaments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.admin = admin;
        this.balance = balance;
        this.matchesP1 = matchesP1;
        this.matchesP2 = matchesP2;
        this.tournaments = tournaments;
    }

    public Set<Match> getMatchesP1() {
        return matchesP1;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void setMatchesP1(Set<Match> matchesP1) {
        this.matchesP1 = matchesP1;
    }

    public Set<Match> getMatchesP2() {
        return matchesP2;
    }

    public void setMatchesP2(Set<Match> matchesP2) {
        this.matchesP2 = matchesP2;
    }

    public String toString() {
        return String.format("ID: %s\nE-mail: %s\nPass: %s\nAdmin: %b\nBalance: %f\n", id, email, password, admin, balance);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }
}