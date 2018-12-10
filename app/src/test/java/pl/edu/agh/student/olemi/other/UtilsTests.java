package pl.edu.agh.student.olemi.other;

import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.util.Calendar;
import java.util.GregorianCalendar;

import pl.edu.agh.student.olemi.utils.DateUtils;

import static org.junit.Assert.assertEquals;

public class UtilsTests {

    @Test
    public void strinDateTest() {
        // given
        final LocalDate date1 = DateUtils.dateFromString("2018-12-11");
        final LocalDate date2 = DateUtils.dateFromString("2020-01-02");

        // when

        // then
        assertEquals("Parsed date should be same", LocalDate.of(2018, 12, 11), date1);
        assertEquals("Parsed date should be same", LocalDate.of(2020, 01, 02), date2);
    }

    @Test
    public void fromCalendarTests() {
        // given
        final GregorianCalendar cal1 = new GregorianCalendar();
        cal1.set(2018, 11 - 1, 10);
        final LocalDate date1 = DateUtils.toLocalDate(cal1);

        // when

        // then
        assertEquals("Converted date should be same", LocalDate.of(2018, 11, 10), date1);
    }

    @Test
    public void toJavaCalendarTest() {
        // given
        final Calendar calendar = DateUtils.toJavaCalendar(LocalDate.of(2012,12,12));

        // when

        // then
        assertEquals("Converted date should be same", calendar.get(Calendar.YEAR), 2012);
        assertEquals("Converted date should be same", calendar.get(Calendar.MONTH), Calendar.DECEMBER);
        assertEquals("Converted date should be same", calendar.get(Calendar.DAY_OF_MONTH), 12);
    }
}
