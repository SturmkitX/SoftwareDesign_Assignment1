package ro.utcluj.bogdan;

import org.junit.Test;
import util.RuntimeUtil;

import static org.junit.Assert.*;

public class EmailTest {

    @Test
    public void checkEmail1() {
        assertFalse(RuntimeUtil.validateEmail(""));
    }

    @Test
    public void checkEmail2() {
        assertFalse(RuntimeUtil.validateEmail("nomail"));
    }

    @Test
    public void checkEmail3() {
        assertTrue(RuntimeUtil.validateEmail("testmail@mail.mail"));
    }
}
