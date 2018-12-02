package pl.edu.agh.student.olemi;

import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.ProductRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import pl.edu.agh.student.olemi.sampledata.ExampleData;

public class CalendarActivity extends AppCompatActivity {

    private UserRepository userRepository;

    private ProductRepository productRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        userRepository = new NoDbUserRepository(getApplicationContext());
        productRepository = new NoDbProductRepository(getApplicationContext());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
        setSupportActionBar(myToolbar);

        createMonthChangedListener();
        createExampleData();
    }

    private void createExampleData() {
        final ExampleData exampleData = new ExampleData(getApplicationContext());
        exampleData.persistGeneratedData(productRepository, userRepository);
    }

    private void createMonthChangedListener() { List<EventDay> events = new ArrayList<>();
//
//        Calendar calendar = Calendar.getInstance();
//        events.add(new EventDay(calendar, R.drawable.goal_achieved));
//        Calendar day = Calendar.getInstance();
//        day.set(2018, Calendar.DECEMBER, 19);
//        Calendar day2 = Calendar.getInstance(); day.set(2018, Calendar.DECEMBER, 19);
//        events.add(new EventDay(day, R.drawable.goal_achieved));
//
//        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
//        calendarView.setEvents(events);
//
//        final OnCalendarPageChangeListener pageChangeListener = () -> {
//
//        };
//
//        calendarView.setOnForwardPageChangeListener(pageChangeListener);
//        calendarView.setOnForwardPageChangeListener(pageChangeListener);
//
    }
}
