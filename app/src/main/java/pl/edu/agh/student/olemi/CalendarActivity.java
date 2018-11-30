package pl.edu.agh.student.olemi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
        setSupportActionBar(myToolbar);

        createDayGoalIcons();
    }

    private void createDayGoalIcons() {
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.goal_achieved));
        Calendar day = Calendar.getInstance();
        day.set(2018, Calendar.NOVEMBER, 10);
        events.add(new EventDay(day, R.drawable.goal_achieved));




        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);
    }
}
