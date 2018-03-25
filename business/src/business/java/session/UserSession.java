package session;

import database.TournamentAccess;
import models.Match;
import models.Tournament;
import models.User;

public final class UserSession {
	
	private static final int TOURNAMENT_SELECT_LIMIT = 12;
	private static final int USER_SELECT_LIMIT = 20;
	
	private static User loggedUser;
	private static Tournament activeTournament;
	private static Match activeMatch;
	private static int tournamentOffset = 0;
	private static int userOffset = 0;
	
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
	
	public static void refreshActiveTournament() {
		activeTournament = TournamentAccess.getTournamentById(activeTournament.getId());
	}
	
	public static int getTournamentOffset() {
		return tournamentOffset;
	}
	
	public static void incrementTournamentOffset() {
		tournamentOffset += TOURNAMENT_SELECT_LIMIT;
	}
	
	public static void decrementTournamentOffset() {
		tournamentOffset -= TOURNAMENT_SELECT_LIMIT;
	}
	
	public static int getUserOffset() {
		return userOffset;
	}
	
	public static void incrementUserOffset() {
		tournamentOffset += USER_SELECT_LIMIT;
	}
	
	public static void decrementUserOffset() {
		tournamentOffset -= USER_SELECT_LIMIT;
	}
}
