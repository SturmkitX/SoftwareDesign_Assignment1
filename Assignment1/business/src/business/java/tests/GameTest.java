package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import logic.GameLogic;
import models.Game;

public class GameTest {
	
	private Game game = new Game(0, 0, 0);

	@Test
	public void testGame1() {
		game.setP1Score(9);
		game.setP2Score(7);
		assertEquals(GameLogic.getWinner(game), 0);
	}
	
	@Test
	public void testGame2() {
		game.setP1Score(11);
		game.setP2Score(7);
		assertEquals(GameLogic.getWinner(game), 1);
	}
	
	@Test
	public void testGame3() {
		game.setP1Score(11);
		game.setP2Score(11);
		assertEquals(GameLogic.getWinner(game), 0);
	}
	
	@Test
	public void testGame4() {
		game.setP1Score(13);
		game.setP2Score(15);
		assertEquals(GameLogic.getWinner(game), 2);
	}

}
