package entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private boolean admin;
    private float balance;

    public User() {

    }

    public User(int id, String email, String password, String name, boolean admin, float balance) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.name = name;
        this.balance = balance;
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