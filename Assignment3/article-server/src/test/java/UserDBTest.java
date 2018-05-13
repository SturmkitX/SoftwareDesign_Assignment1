import database.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.User;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserDBTest {

    private User u;
    private User u2;

    @Before
    public void init() {
        u = new User();
        u.setRole(2);
        u.setPassword("pass");
        u.setEmail("unittest@junit.org");
        u.setName("Unit Test");
        UserDAO.insertWriter(u);
    }

    @After
    public void clean() {
        UserDAO.deletetWriter(u);

        if(u2 != null && u2.getId() > 0) {
            UserDAO.deletetWriter(u2);
            u2 = null;
        }
    }

    @Test
    public void test1() {
        // insert a different user
        u2 = new User();
        u2.setRole(2);
        u2.setPassword("pass");
        u2.setEmail("unittest2@junit.org");
        u2.setName("Unit Test");
        UserDAO.insertWriter(u2);

        assertTrue(u2.getId() > 0);
    }

    @Test
    public void test2() {
        // insert the same user
        u2 = new User();
        u2.setRole(2);
        u2.setPassword("pass");
        u2.setEmail("unittest@junit.org");
        u2.setName("Unit Test");
        UserDAO.insertWriter(u2);

//        System.out.println(u2.getId());

        assertTrue(u2.getId() < 0);
    }

    @Test
    public void test3() {
        // delete a non-existing user
        u2 = new User();
        u2.setRole(2);
        u2.setPassword("pass");
        u2.setEmail("unittest@junit.org");
        u2.setName("Unit Test");
        u2.setId(u.getId() + 1);
        UserDAO.deletetWriter(u2);

        u2 = UserDAO.findUser(u2.getId());

        assertNull(u2);
    }
}
