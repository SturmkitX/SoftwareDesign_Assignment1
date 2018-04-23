package ro.utcluj.bogdan;

import entities.Match;
import entities.Tournament;
import entities.User;
import implementations.hibernate.UserHibernate;
import implementations.mysql.UserDAOImplem;
import interfaces.UserDAO;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class LogInTest {
    private User user = new User(6, "dorinb@juego.com", "maidorin", "Dorin B.", false, 20,
            new HashSet<Match>(), new HashSet<Match>(), new HashSet<Tournament>());
    private UserDAO ud1 = new UserDAOImplem();
    private UserDAO ud2 = new UserHibernate();

    @Test
    public void testLoginDAO1() {
        assertFalse(ud1.findUserById(5).equals(user));
    }

    @Test
    public void testLoginDAO2() {
        assertTrue(ud1.findUserById(6).equals(user));
    }

    @Test
    public void testLoginHibernate1() {
        assertFalse(ud2.findUserById(5).equals(user));
    }

    @Test
    public void testLoginHibernate2() {
        assertTrue(ud2.findUserById(6).equals(user));
    }
}
