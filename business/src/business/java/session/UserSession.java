package session;

import models.Match;
import models.Tournament;
import models.User;

public final class UserSession {
	
	private static User loggedUser;
	private static Tournament activeTournament;
	private static Match activeMatch;
	
	private UserSession() {
		
	}
	
	public static void setLoggedInUser(User user) {
		loggedUser = user;
	}
	
	public static User getLoggedInUser() {
		return loggedUser;
	}
	
	public static void setActiveMatch(Match match) {
		activeMatch = match;
	}
	
	public static Match getActiveMatch() {
		return activeMatch;
	}
	
	public static void setActiveTournament(Tournament tournament) {
		activeTournament = tournament;
	}
	
	public static Tournament getActiveTournament() {
		return activeTournament;
	}
}
