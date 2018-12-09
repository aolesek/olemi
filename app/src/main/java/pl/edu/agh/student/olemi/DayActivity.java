package pl.edu.agh.student.olemi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.helpers.MealAdapter;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.ProductRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import timber.log.Timber;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;

import static pl.edu.agh.student.olemi.utils.DateTimeUtils.*;

public class DayActivity extends AppCompatActivity {

    public static final String SELECTED_DAY = "pl.edu.agh.student.olemi.day.SELECTED_DAY";

    private UserRepository userRepository;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.day_toolbar);
        setSupportActionBar(myChildToolbar);

        userRepository = new NoDbUserRepository(getApplicationContext());

        initRepo();

        List<MealModel> productModels = new ArrayList<>();

        listView = findViewById(R.id.list21);

        fetchMeals();

        TextView allCalories = findViewById(R.id.allCalories);
        TextView allNutrients = findViewById(R.id.allNutrients);
        allCalories.setText("1200/2500kcal");
        allNutrients.setText("C: 50g P: 15g F: 33g");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DayActivity.this, AddMealActivity.class);
                startActivity(i);
            }
        });

    }

    @SuppressLint("CheckResult")
    public void fetchMeals() {
        final Intent intent = getIntent();
        final String message = Objects.nonNull(intent.getStringExtra(SELECTED_DAY))
                ? intent.getStringExtra(SELECTED_DAY)
                : calendarDateToString(Calendar.getInstance());

        Timber.i("Fetching meals for " + message);

        userRepository.getMeals(message).subscribe(meals -> {
            final MealAdapter mealAdapter = new MealAdapter(getApplicationContext(), meals);

            DayActivity.this.listView.post(() -> {
                ListView lV = findViewById(R.id.list21);
                lV.setAdapter(mealAdapter);
            });
        });
    }

    public void initRepo() {
        NoDbProductRepository npr = new NoDbProductRepository(getApplicationContext());
        pl.edu.agh.student.olemi.entities.Nutrients nutrients = NutrientsBuilder.aNutrients()
                .withCalories(100d)
                .withCarbohydrates(12d)
                .withFats(34d)
                .withProtein(14d)
                .build();
        SimpleProductModel sp = new SimpleProductModel("maryna", nutrients);
        SimpleProductModel sp2 = new SimpleProductModel("kasztan", nutrients);
        SimpleProductModel sp3 = new SimpleProductModel("smietana", nutrients);
        npr.insertProduct(sp).subscribe();
        npr.insertProduct(sp2).subscribe();
        npr.insertProduct(sp3).subscribe();
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
}
