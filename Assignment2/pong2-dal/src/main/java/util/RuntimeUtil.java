package util;

import database.factory.Factory;
import entities.Game;
import entities.Match;
import entities.User;

import java.util.regex.Pattern;

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

    public static boolean validateEmail(String email) {
        return Pattern.compile("\\S+@\\S+\\.\\S+").matcher(email).find();
    }

    public static int getWinner(Game game) {
        if(game.getP1Score() < 11 && game.getP2Score() < 11) {
            return 0;
        }

        int scoreDiff = game.getP1Score() - game.getP2Score();

        if(game.getP1Score() >= 11 && scoreDiff >= 2) {
            return 1;
        }

        if(game.getP2Score() >= 11 && scoreDiff <= -2) {
            return 2;
        }

        return 0;
    }

    public static int getWinner(Match match) {
        if(match.getGames().size() < 3) {
            return 0;
        }

        int p1Points = 0;
        int p2Points = 0;
        for(Game g : match.getGames()) {
            int partResult = getWinner(g);
            switch(partResult) {
                case 1 : p1Points++; break;
                case 2 : p2Points++; break;
                default : 	// nothing
            }
        }

        if(p1Points >= 3) {
            return 1;
        }

        if(p2Points >= 3) {
            return 2;
        }

        return 0;
    }


}
