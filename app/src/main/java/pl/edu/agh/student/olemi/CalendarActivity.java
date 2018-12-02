package pl.edu.agh.student.olemi;

import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;
import pl.edu.agh.student.olemi.repositories.ProductRepository;
import pl.edu.agh.student.olemi.sampledata.ExampleData;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
        setSupportActionBar(myToolbar);

        createDayGoalIcons();
        dbTests();
    }

    private void createDayGoalIcons() {
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(calendar, R.drawable.goal_achieved));
        Calendar day = Calendar.getInstance();
        day.set(2018, Calendar.DECEMBER, 19);
        Calendar day2 = Calendar.getInstance(); day.set(2018, Calendar.DECEMBER, 19);
        events.add(new EventDay(day, R.drawable.goal_achieved));




        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setEvents(events);
    }

    private void dbTests() {
        final ProductRepository repository = new NoDbProductRepository(getApplicationContext());
        final Nutrients nutrients = NutrientsBuilder.aNutrients()
                .withCalories(100)
                .withCarbohydrates(12)
                .withFats(34)
                .withProtein(14)
                .build();
        ExampleData exampleData = new ExampleData(getApplicationContext());
    }
}
