package pl.edu.agh.student.olemi.utils;

import android.icu.util.Calendar;

import org.threeten.bp.LocalDate;

public class DateUtils {

    public static java.util.Calendar toJavaCalendar(LocalDate time) {
        java.util.Calendar instance = java.util.Calendar.getInstance();
        instance.set(java.util.Calendar.YEAR, time.getYear());
        instance.set(java.util.Calendar.MONTH, time.getMonthValue() - 1);
        instance.set(java.util.Calendar.DATE, time.getDayOfMonth());
        return instance;
    }

    public static LocalDate toLocalDate(java.util.Calendar calendar) {
        return LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static LocalDate dateFromString(String date) {
        return LocalDate.parse(date);
    }
}
