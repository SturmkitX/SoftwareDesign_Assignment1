package util;

import database.factory.Factory;
import entities.Match;
import entities.User;
import database.factory.FactoryProducer;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class UserSession {

    private static User logged = null;
    private static Factory factory = null;
    private static String factoryString = null;
    private static Match activeMatch = null;

    public static Stage getStage() {
        return stage;
    }

    public static String getFactoryString() {
        return factoryString;
    }

    public static void setFactoryString(String factoryString) {
        UserSession.factoryString = factoryString;
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

    public static Match getActiveMatch() {
        return activeMatch;
    }

    public static void setActiveMatch(Match activeMatch) {
        UserSession.activeMatch = activeMatch;
    }

    public static void setFactory(String factory) {
        try {
            FileWriter writer = new FileWriter("connectionType");
            writer.write(factory);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UserSession.factoryString = factory;
        UserSession.factory = FactoryProducer.getFactory(factory);
    }


}
