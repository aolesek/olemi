package pl.edu.agh.student.olemi;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.ProductRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import pl.edu.agh.student.olemi.sampledata.ExampleData;
import pl.edu.agh.student.olemi.utils.Constants;
import timber.log.Timber;

import static pl.edu.agh.student.olemi.utils.DateTimeUtils.calendarDateToString;
import static pl.edu.agh.student.olemi.utils.DateTimeUtils.toAndroidCalendar;
import static pl.edu.agh.student.olemi.utils.DateTimeUtils.toJavaCalendar;

public class CalendarActivity extends AppCompatActivity {

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private CalendarView calendarView;

    private Map<String, EventDay> eventDays = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        userRepository = new NoDbUserRepository(getApplicationContext());
        productRepository = new NoDbProductRepository(getApplicationContext());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
        setSupportActionBar(myToolbar);

        this.calendarView = (CalendarView) findViewById(R.id.calendarView);

        fillEvents();
        addOnDayClickListener();
    }

    private void addOnDayClickListener() {
        calendarView.setOnDayClickListener((eventDay) -> {
            final Calendar clickedDayCalendar = toAndroidCalendar(eventDay.getCalendar());
            Intent intent = new Intent(this, DayActivity.class);
            intent.putExtra(DayActivity.SELECTED_DAY, calendarDateToString(clickedDayCalendar));
            startActivity(intent);
        });
    }

    private void fillEvents() {
        IntStream.range(0, Constants.CALENDAR_STATUS_FOR_LAST_N_DAYS).forEach(this::resolveGoalDataAndAddIcons);
    }

    private void resolveGoalDataAndAddIcons(int dayNumber) {
        final Calendar day = Calendar.getInstance();
        day.add(Calendar.DATE, -dayNumber);
        userRepository.getCaloriesGoalStats(day).subscribe(goal -> addEventIcons(goal, day));
    }

    private void addEventIcons(Pair<Integer, Integer> goalStatus, Calendar currentDay) {
        final Integer goalStatusAsset = getGoalStatusAsset(goalStatus);
        if (Objects.nonNull(goalStatusAsset))
            eventDays.put(calendarDateToString(currentDay), new EventDay(toJavaCalendar(currentDay), getGoalStatusAsset(goalStatus)));

        final Calendar today = Calendar.getInstance();
        long daysBetween = TimeUnit.MILLISECONDS.toDays(Math.abs(today.getTimeInMillis() - currentDay.getTimeInMillis()));

        if (daysBetween >= Constants.CALENDAR_STATUS_FOR_LAST_N_DAYS - 1)
            calendarView.setEvents(new ArrayList<EventDay>(eventDays.values()));
    }

    private Integer getGoalStatusAsset(Pair<Integer, Integer> goalStatus) {
        int diff = goalStatus.second - goalStatus.first;
        if (diff < Constants.GOAL_EXCEEDED_THRESHOLD) return R.drawable.goal_exceeded;
        else if (diff < Constants.GOAL_ACHIEVED_THRESHOLD) return R.drawable.goal_achieved;
        else if (diff < Constants.GOAL_NEAR_THRESHOLD) return R.drawable.goal_near;
        else if (diff < Constants.GOAL_INSUFFICIENT_THRESHOLD) return R.drawable.goal_insufficient;
        else return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar_action_goals:
                Intent intent = new Intent(this, GoalSettings.class);
                startActivity(intent);
                return true;

            case R.id.calendar_action_stats:
                Toast wtfToast = Toast.makeText(getApplicationContext(), "not supported yet", Toast.LENGTH_SHORT);
                wtfToast.show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
