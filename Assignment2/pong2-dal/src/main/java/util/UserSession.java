package util;

import database.factory.Factory;
import entities.User;
import database.factory.FactoryProducer;
import javafx.stage.Stage;

public class UserSession {

    private static User logged = null;
    private static Factory factory = null;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        UserSession.stage = stage;
    }

    private static Stage stage = null;

    private UserSession() {

    }

    public static User getLogged() {
        return logged;
    }

    public static void setLogged(User logged) {
        UserSession.logged = logged;
    }

    public static Factory getFactory() {
        return factory;
    }

    public static void setFactory(String factory) {
        UserSession.factory = FactoryProducer.getFactory(factory);
    }


}
