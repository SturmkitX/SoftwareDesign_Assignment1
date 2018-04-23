package ro.utcluj.bogdan;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import entities.Game;
import entities.Match;
import util.RuntimeUtil;

public class MatchTest {

    private Set<Game> games = new HashSet<>();
    private Match match = new Match(0, null, null, 1, games, null);

    @Test
    public void testMatch1() {
        games.clear();
        games.add(new Game(0, 11, 9, null));
        games.add(new Game(0, 11, 7, null));
        games.add(new Game(0, 11, 5, null));
        assertEquals(RuntimeUtil.getWinner(match), 1);
    }

    @Test
    public void testMatch2() {
        games.clear();
        games.add(new Game(0, 11, 9, null));
        games.add(new Game(0, 12, 14, null));
        games.add(new Game(0, 11, 5, null));
        games.add(new Game(0, 5, 11, null));
        games.add(new Game(0, 6, 11, null));
        assertEquals(RuntimeUtil.getWinner(match), 2);
    }

    @Test
    public void testMatch3() {
        games.clear();
        games.add(new Game(0, 11, 9, null));
        games.add(new Game(0, 12, 14, null));
        assertEquals(RuntimeUtil.getWinner(match), 0);
    }

}