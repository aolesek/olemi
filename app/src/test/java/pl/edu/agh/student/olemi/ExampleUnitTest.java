package pl.edu.agh.student.olemi;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {
//        final LocalDate now = LocalDate.now();
//        System.out.println(now.toString());
        assertEquals(4, 2 + 2);
    }
}