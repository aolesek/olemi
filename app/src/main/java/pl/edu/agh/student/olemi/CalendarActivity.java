package pl.edu.agh.student.olemi;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import pl.edu.agh.student.olemi.utils.Constants;
import timber.log.Timber;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;
import static pl.edu.agh.student.olemi.utils.DateUtils.toJavaCalendar;
import static pl.edu.agh.student.olemi.utils.DateUtils.toLocalDate;

public class CalendarActivity extends AppCompatActivity {

    private UserRepository userRepository;

    private CalendarView calendarView;

    private Map<String, EventDay> eventDays = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        userRepository = new NoDbUserRepository(getApplicationContext(), true);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
        setSupportActionBar(myToolbar);

        this.calendarView = (CalendarView) findViewById(R.id.calendarView);

        fillEvents();
        addOnDayClickListener();
    }

    private void addOnDayClickListener() {
        calendarView.setOnDayClickListener((eventDay) -> {
            final LocalDate date = toLocalDate(eventDay.getCalendar());
            Intent intent = new Intent(this, DayActivity.class);
            Timber.i("Showing DayActivity for " + date.toString());
            intent.putExtra(DayActivity.SELECTED_DAY, date.toString());
            startActivity(intent);
        });
    }

    private void fillEvents() {
        IntStream.range(0, Constants.CALENDAR_STATUS_FOR_LAST_N_DAYS).forEach(this::resolveGoalDataAndAddIcons);
    }

    private void resolveGoalDataAndAddIcons(int dayNumber) {
        final LocalDate day = LocalDate.now().minusDays(dayNumber);
        userRepository.getCaloriesGoalStats(day.toString()).subscribe(goal -> addEventIcons(goal, day));
    }

    private void addEventIcons(Pair<Double, Integer> goalStatus, LocalDate currentDay) {
        final Integer goalStatusAsset = getGoalStatusAsset(goalStatus);
        if (Objects.nonNull(goalStatusAsset))
            eventDays.put(currentDay.toString(), new EventDay(toJavaCalendar(currentDay),
                    getGoalStatusAsset(goalStatus)));

        final LocalDate today = LocalDate.now();
        long daysBetween = DAYS.between(currentDay, today);

        if (daysBetween >= Constants.CALENDAR_STATUS_FOR_LAST_N_DAYS - 1)
            calendarView.setEvents(new ArrayList<EventDay>(eventDays.values()));
    }

    private Integer getGoalStatusAsset(Pair<Double, Integer> goalStatus) {
        double diff = goalStatus.second - goalStatus.first;
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
                Intent statsIntent = new Intent(this, StatsActivity.class);
                startActivity(statsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
