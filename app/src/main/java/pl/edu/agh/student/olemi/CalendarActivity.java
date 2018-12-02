package pl.edu.agh.student.olemi;

import android.icu.util.Calendar;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

import static pl.edu.agh.student.olemi.utils.DateTimeUtils.toAndroidCalendar;
import static pl.edu.agh.student.olemi.utils.DateTimeUtils.toJavaCalendar;

public class CalendarActivity extends AppCompatActivity {

    private UserRepository userRepository;

    private ProductRepository productRepository;

    private CalendarView calendarView;

    private List<EventDay> eventDays = new LinkedList<>();

    private int currentMaxDay = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        userRepository = new NoDbUserRepository(getApplicationContext());
        productRepository = new NoDbProductRepository(getApplicationContext());
        createExampleData();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.calendar_toolbar);
        setSupportActionBar(myToolbar);

        createMonthChangedListener();
    }

    private void createExampleData() {
        final ExampleData exampleData = new ExampleData(getApplicationContext());
        exampleData.persistGeneratedData(productRepository, userRepository);
    }

    private void createMonthChangedListener() {
        List<EventDay> events = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        events.add(new EventDay(toJavaCalendar(calendar), R.drawable.goal_achieved));
        Calendar day = Calendar.getInstance();
        day.set(2018, Calendar.DECEMBER, 19);
        Calendar day2 = Calendar.getInstance();
        day.set(2018, Calendar.DECEMBER, 19);
        events.add(new EventDay(toJavaCalendar(day), R.drawable.goal_achieved));

        this.calendarView = (CalendarView) findViewById(R.id.calendarView);

        final OnCalendarPageChangeListener pageChangeListener = () -> {
            Calendar currentPageDate = toAndroidCalendar(calendarView.getCurrentPageDate());
            this.currentMaxDay = currentPageDate.getActualMaximum(Calendar.DAY_OF_MONTH);
            eventDays.clear();
            IntStream.range(1, currentMaxDay + 1).forEach(dayNumber -> resolveGoalData(dayNumber, currentPageDate));
        };
        pageChangeListener.onChange();

        calendarView.setOnForwardPageChangeListener(pageChangeListener);
        calendarView.setOnPreviousPageChangeListener(pageChangeListener);
    }

    private void resolveGoalData(int dayNumber, Calendar currentPageDate) {
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(Calendar.YEAR, currentPageDate.get(Calendar.YEAR));
        currentDay.set(Calendar.MONTH, currentPageDate.get(Calendar.MONTH));
        currentDay.set(Calendar.DATE, dayNumber);
        Calendar today = Calendar.getInstance();
        if (today.after(currentDay)) {
            userRepository.getCaloriesGoalStats(currentDay).subscribe(integerIntegerPair -> addEventIcons(integerIntegerPair, currentDay));
        }
    }

    private void addEventIcons(Pair<Integer, Integer> goalStatus, Calendar currentDay) {
        Integer goalStatusAsset = getGoalStatusAsset(goalStatus);
        Objects.nonNull(null);
        if (Objects.nonNull(goalStatusAsset)) {
            eventDays.add(new EventDay(toJavaCalendar(currentDay), getGoalStatusAsset(goalStatus)));
            Calendar today = Calendar.getInstance();
            if (eventDays.size() >= currentMaxDay || ((currentDay.get(Calendar.MONTH) == today.get(Calendar.MONTH)) && eventDays.size() >= today.get(Calendar.DATE) - 1)) {
                CalendarActivity.this.calendarView.post(() -> {
                    this.calendarView.setEvents(eventDays);
                });
            }
        }
    }

    private Integer getGoalStatusAsset(Pair<Integer, Integer> goalStatus) {
        int diff = goalStatus.second - goalStatus.first;
        if (diff < Constants.GOAL_EXCEEDED_THRESHOLD) {
            return R.drawable.goal_exceeded;
        } else if (diff < Constants.GOAL_ACHIEVED_THRESHOLD) {
            return R.drawable.goal_achieved;
        } else if (diff < Constants.GOAL_NEAR_THRESHOLD) {
            return R.drawable.goal_near;
        } else if (diff < Constants.GOAL_INSUFFICIENT_THRESHOLD) {
            return R.drawable.goal_insufficient;
        } else return null;
    }
}
