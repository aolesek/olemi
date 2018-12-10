package pl.edu.agh.student.olemi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.threeten.bp.LocalDate;

import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.helpers.MealAdapter;
import pl.edu.agh.student.olemi.models.UserDataModel;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import timber.log.Timber;
import pl.edu.agh.student.olemi.models.MealModel;

public class DayActivity extends AppCompatActivity {

    public static final String SELECTED_DAY = "pl.edu.agh.student.olemi.day.SELECTED_DAY";

    private UserRepository userRepository;

    private ListView listView;

    private String dayMessage;

    MealAdapter mealAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.day_toolbar);
        setSupportActionBar(myChildToolbar);

        userRepository = new NoDbUserRepository(getApplicationContext(), true);

        listView = findViewById(R.id.list21);

        fetchMeals();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(DayActivity.this, AddMealActivity.class);
            startActivity(i);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        final Intent intent = getIntent();
        dayMessage = Objects.nonNull(intent.getStringExtra(SELECTED_DAY))
                ? intent.getStringExtra(SELECTED_DAY)
                : LocalDate.now().toString();

        fillSummary();

        userRepository.getMeals(dayMessage).subscribe(meals -> {
            mealAdapter = new MealAdapter(getApplicationContext(), meals);
            mealAdapter.notifyDataSetChanged();

            for (MealModel m : meals) {
                System.out.println(m.getName());
            }
        });
    }

    @SuppressLint("CheckResult")
    public void fetchMeals() {
        final Intent intent = getIntent();
        final String message = Objects.nonNull(intent.getStringExtra(SELECTED_DAY))
                ? intent.getStringExtra(SELECTED_DAY)
                : LocalDate.now().toString();

        Timber.i("Fetching meals for " + message);

        userRepository.getMeals(message).subscribe(meals -> {
            mealAdapter = new MealAdapter(getApplicationContext(), meals);

            DayActivity.this.listView.post(() -> {
                ListView lV = findViewById(R.id.list21);
                lV.setAdapter(mealAdapter);
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.day_actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();

        switch (item.getItemId()) {
            case R.id.day_action_calendar:
                Intent myIntent = new Intent(DayActivity.this, CalendarActivity.class);
                DayActivity.this.startActivity(myIntent);
                return true;

            case R.id.day_action_settings:
                Toast wtfToast = Toast.makeText(context, "WTF no settings here you silly!", Toast.LENGTH_SHORT);
                wtfToast.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void fillSummary() {
        final TextView caloriesText = (TextView) findViewById(R.id.daySummaryAll);
        final ProgressBar caloriesBar = (ProgressBar) findViewById(R.id.dayCalorieBar);

        final TextView carbsText = (TextView) findViewById(R.id.daySummaryCarbs);
        final ProgressBar carbsBar = (ProgressBar) findViewById(R.id.dayCarbsBar);

        final TextView fatsText = (TextView) findViewById(R.id.daySummaryFats);
        final ProgressBar fatsBar = (ProgressBar) findViewById(R.id.dayFatsBar);

        final TextView proteinsText = (TextView) findViewById(R.id.daySummaryProteins);
        final ProgressBar proteinsBar = (ProgressBar) findViewById(R.id.dayProteinsBar);


        userRepository.getFullGoalStats(dayMessage).subscribe(stats -> {
            caloriesText.setText(createCalorieSummary(stats));
            caloriesBar.setMax(stats.second.getCaloriesGoal());
            caloriesBar.setProgress((int) ((double) stats.first.calories));

            carbsText.setText(String.format("C: %.0f/%d g", stats.first.carbohydrates, stats.second.getCarbonhydrateGoal()));
            carbsBar.setMax(stats.second.getCarbonhydrateGoal());
            carbsBar.setProgress((int) ((double) stats.first.carbohydrates));

            fatsText.setText(String.format("F: %.0f/%d g", stats.first.fats, stats.second.getFatGoal()));
            fatsBar.setMax(stats.second.getFatGoal());
            fatsBar.setProgress((int) ((double) stats.first.fats));

            proteinsText.setText(String.format("P: %.0f/%d g", stats.first.protein, stats.second.getProteinGoal()));
            proteinsBar.setMax(stats.second.getProteinGoal());
            proteinsBar.setProgress((int) ((double) stats.first.protein));
        });
    }

    private String createCalorieSummary(Pair<Nutrients, UserDataModel> stats) {
        final Double current = stats.first.calories;
        final Integer goal = stats.second.getCaloriesGoal();
        final Double difference = Math.abs(current - goal);
        final String statusSuffix = current <= goal ? "remaining" : "exceeded";
        return String.format("Cal: %.0f/%d kcal. (%.0f %s)", current, goal, difference, statusSuffix);
    }
}
