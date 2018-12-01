package pl.edu.agh.student.olemi;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.helpers.MealAdapter;
import pl.edu.agh.student.olemi.model.Nutrients;
import pl.edu.agh.student.olemi.model.ProductModel;
import pl.edu.agh.student.olemi.model.SimpleProduct;

public class DayActivity extends AppCompatActivity {

    MealAdapter mealAdapter;
    public static final String SELECTED_DAY = "pl.edu.agh.student.olemi.day.SELECTED_DAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);

      List<ProductModel> productModels = new ArrayList<>();
      productModels.add(new SimpleProduct("jajko", new Nutrients(2.137, 2.137, 2.137, 2.137)));
      productModels.add(new SimpleProduct("bożena", new Nutrients(2.137, 2.137, 2.137, 2.137)));
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
}
