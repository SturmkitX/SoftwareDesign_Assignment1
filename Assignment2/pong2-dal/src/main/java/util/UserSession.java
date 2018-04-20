package util;

import database.factory.Factory;
import entities.User;
import database.factory.FactoryProducer;

public class UserSession {

    private static User logged = null;
    private static Factory factory = null;

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
