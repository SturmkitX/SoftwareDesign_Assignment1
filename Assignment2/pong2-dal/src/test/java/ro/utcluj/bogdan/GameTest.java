package ro.utcluj.bogdan;

import static org.junit.Assert.*;

import org.junit.Test;

import entities.Game;
import util.RuntimeUtil;

public class GameTest {

    private Game game = new Game(0, 0, 0, null);

    @Test
    public void testGame1() {
        game.setP1Score(9);
        game.setP2Score(7);
        assertEquals(RuntimeUtil.getWinner(game), 0);
    }

    @Test
    public void testGame2() {
        game.setP1Score(11);
        game.setP2Score(7);
        assertEquals(RuntimeUtil.getWinner(game), 1);
    }

    @Test
    public void testGame3() {
        game.setP1Score(11);
        game.setP2Score(11);
        assertEquals(RuntimeUtil.getWinner(game), 0);
    }

    @Test
    public void testGame4() {
        game.setP1Score(13);
        game.setP2Score(15);
        assertEquals(RuntimeUtil.getWinner(game), 2);
    }

}