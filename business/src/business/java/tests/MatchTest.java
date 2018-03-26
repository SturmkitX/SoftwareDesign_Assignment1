package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.MatchLogic;
import models.Game;
import models.Match;

public class MatchTest {
	
	private List<Game> games = new ArrayList<Game>();
	private Match match = new Match(0, null, null, 1, games);

	@Test
	public void testMatch1() {
		games.clear();
		games.add(new Game(0, 11, 9));
		games.add(new Game(0, 11, 7));
		games.add(new Game(0, 11, 5));
		assertEquals(MatchLogic.getWinner(match), 1);
	}
	
	@Test
	public void testMatch2() {
		games.clear();
		games.add(new Game(0, 11, 9));
		games.add(new Game(0, 12, 14));
		games.add(new Game(0, 11, 5));
		games.add(new Game(0, 5, 11));
		games.add(new Game(0, 6, 11));
		assertEquals(MatchLogic.getWinner(match), 2);
	}
	
	@Test
	public void testMatch3() {
		games.clear();
		games.add(new Game(0, 11, 9));
		games.add(new Game(0, 12, 14));
		assertEquals(MatchLogic.getWinner(match), 0);
	}

}
