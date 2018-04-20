package util;

import database.factory.Factory;
import entities.User;

public class RuntimeUtil {
    private RuntimeUtil() {

    }

    public static User logIn(String username, String password) {
        Factory factory = UserSession.getFactory();
        // System.out.println("Factory is null: " + (factory == null));
        User user = factory.getUserDatabase().findUserByEmailAndPassword(username, password);

        if(user != null) {
            UserSession.setLogged(user);
        }
        return user;
    }
}
