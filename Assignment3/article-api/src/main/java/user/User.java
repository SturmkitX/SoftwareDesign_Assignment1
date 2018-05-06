package user;

public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private int role;

    public User() {

    }

    public User(User u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.password = u.getPassword();
        this.name = u.getName();
        this.role = u.getRole();
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}
