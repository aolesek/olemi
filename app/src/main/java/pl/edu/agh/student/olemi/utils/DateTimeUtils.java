package pl.edu.agh.student.olemi.utils;

import android.icu.util.Calendar;

public class DateTimeUtils {

    public static String STRING_DATE_SEPARATOR = ";";

    public static java.util.Calendar toJavaCalendar(Calendar calendar) {
        java.util.Calendar instance = java.util.Calendar.getInstance();
        instance.set(java.util.Calendar.YEAR, calendar.get(Calendar.YEAR));
        instance.set(java.util.Calendar.MONTH, calendar.get(Calendar.MONTH));
        instance.set(java.util.Calendar.DATE, calendar.get(Calendar.DATE));
        return instance;
    }

    public static Calendar toAndroidCalendar(java.util.Calendar calendar) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.YEAR, calendar.get(java.util.Calendar.YEAR));
        instance.set(Calendar.MONTH, calendar.get(java.util.Calendar.MONTH));
        instance.set(Calendar.DATE, calendar.get(java.util.Calendar.DATE));
        return instance;
    }

    public static Calendar clearCalendar(Calendar calendar) {
        calendar.clear(Calendar.MILLISECOND);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.HOUR);
        return calendar;
    }

    public static String calendarDateToString(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DATE);
        return "" + year + STRING_DATE_SEPARATOR + month + STRING_DATE_SEPARATOR + day;
    }

    public static Calendar stringDateToCalendar(String date) {
        String[] dateAsStringArray = date.split(STRING_DATE_SEPARATOR);
        int year = Integer.parseInt(dateAsStringArray[0]);
        int month = Integer.parseInt(dateAsStringArray[1]);
        int day = Integer.parseInt(dateAsStringArray[2]);

        Calendar instance = Calendar.getInstance();
        instance.clear();
        instance.set(year, month, day);
        return instance;
    }
}
