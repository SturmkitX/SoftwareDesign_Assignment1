package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import user.User;

import java.util.Objects;

public class UserDTO {

    private User source;
    private IntegerProperty id;
    private StringProperty email;
    private StringProperty password;
    private StringProperty name;

    public UserDTO(User src) {
        this.source = src;
        this.id = new SimpleIntegerProperty(src.getId());
        this.email = new SimpleStringProperty(src.getEmail());
        this.password = new SimpleStringProperty(src.getPassword());
        this.name = new SimpleStringProperty(src.getName());
    }

    public User getSource() {
        return source;
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

}
