package models;

import java.util.List;

public class Match {
	private int id;
	private User p1;
	private User p2;
	private List<Game> games;
	
	public Match(int id, User p1, User p2, List<Game> games) {
		this.id = id;
		this.p1 = p1;
		this.p2 = p2;
		this.games = games;
	}
	
	public int getId() {
		return id;
	}
	
	public User getP1() {
		return p1;
	}
	
	public User getP2() {
		return p2;
	}
	
	public List<Game> getGames() {
		return games;
	}
}
