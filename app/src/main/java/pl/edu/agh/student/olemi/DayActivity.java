package pl.edu.agh.student.olemi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.helpers.MealAdapter;
import pl.edu.agh.student.olemi.logging.DebugLogTree;
import pl.edu.agh.student.olemi.model_maciek.Nutrients;
import pl.edu.agh.student.olemi.model_maciek.ProductModel;
import pl.edu.agh.student.olemi.model_maciek.SimpleProduct;
import timber.log.Timber;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;

public class DayActivity extends AppCompatActivity {

    MealAdapter mealAdapter;
    public static final String SELECTED_DAY = "pl.edu.agh.student.olemi.day.SELECTED_DAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

        Timber.plant(new DebugLogTree());

        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.day_toolbar);
        setSupportActionBar(myChildToolbar);

        initRepo();

//
        List<MealModel> productModels = new ArrayList<>();

//        NoDbProductRepository npr = new NoDbProductRepository(getApplicationContext());
//        npr.getProductsWithLimit(10).subscribe(t -> productModels.addAll(t));

        mealAdapter = new MealAdapter(this, productModels);
        ListView listView = findViewById(R.id.list21);
        listView.setAdapter(mealAdapter);

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

    public void initRepo(){
        NoDbProductRepository npr = new NoDbProductRepository(getApplicationContext());
        pl.edu.agh.student.olemi.entities.Nutrients nutrients = NutrientsBuilder.aNutrients()
                .withCalories(100)
                .withCarbohydrates(12)
                .withFats(34)
                .withProtein(14)
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
