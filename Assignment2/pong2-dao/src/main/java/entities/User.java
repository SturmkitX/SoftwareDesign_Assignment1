package entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private final boolean isAdmin;
    private float balance;

    public User(int id, String email, String password, String name, boolean isAdmin, float balance) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return String.format("ID: %s\nE-mail: %s\nPass: %s\nAdmin: %b\n", id, email, password, isAdmin);
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getBalance() {
        return balance;
    }
}