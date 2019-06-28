package mx.com.ia.androidtesterapp;

import org.junit.Before;
import org.junit.Test;
import mx.com.ia.androidtesterapp.Objects.User;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    /******* Simples test ****/
    @Test
    public void thisIsATest() {
        User user = new User(1234, "name");
        assertEquals(user.getId(), 1234);
    }

    @Test
    public void thisIsAnotherTest() {
        User user = new User(1234, "name");
        assertEquals(user.getUserName(), 1234);
    }

    /******** Evita tener multiples instancias de un solo objeto ******/
    User user;

    @Before
    public void setUp() {
        user = new User(1234, "name");
    }

    @Test
    public void thisIsATest2() {
        assertEquals(user.getId(), 1234);
        thisIsAnotherTest2();
    }

    @Test
    public void thisIsAnotherTest2() {
        assertEquals(user.getUserName(), "name");
    }
}