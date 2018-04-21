package dtos;
;
import entities.User;
import javafx.beans.property.*;

public class UserDTO {
    private User source;
    private IntegerProperty id;
    private StringProperty email;
    private StringProperty password;
    private StringProperty name;
    private BooleanProperty admin;
    private StringProperty balance;

    public UserDTO(User user) {
        this.source = user;
        this.id = new SimpleIntegerProperty(user.getId());
        this.email = new SimpleStringProperty(user.getEmail());
        this.password = new SimpleStringProperty(user.getPassword());
        this.name = new SimpleStringProperty(user.getName());
        this.admin = new SimpleBooleanProperty(user.isAdmin());
        this.balance = new SimpleStringProperty("" + user.getBalance());
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

    public User getSource() {
        return source;
    }

    public void setSource(User source) {
        this.source = source;
    }
}
