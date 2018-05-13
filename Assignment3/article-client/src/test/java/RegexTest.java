import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegexTest {

    private Pattern pattern = Pattern.compile("\\S+@\\S+\\.\\S+");

    @Test
    public void test1() {
        assertTrue(pattern.matcher("test@tesus.com").find());
    }

    @Test
    public void test2() {
        assertFalse(pattern.matcher("test@tesus").find());
    }

    @Test
    public void test3() {
        assertTrue(pattern.matcher("t@t.c").find());
    }

    @Test
    public void test4() {
        assertFalse(pattern.matcher("testtesus.com").find());
    }
}
