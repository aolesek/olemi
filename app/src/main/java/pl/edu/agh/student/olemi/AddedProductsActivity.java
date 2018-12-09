package pl.edu.agh.student.olemi;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import pl.edu.agh.student.olemi.helpers.AddedProductsAdapter;
import pl.edu.agh.student.olemi.helpers.HoldInfo;
import pl.edu.agh.student.olemi.helpers.SearchMealAdapter;
import pl.edu.agh.student.olemi.model_maciek.SimpleProduct;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;

public class AddedProductsActivity extends AppCompatActivity {

    HoldInfo holdInfo = HoldInfo.getInstance();
    AddedProductsAdapter apa;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_products);
        initList();

        userRepository = new NoDbUserRepository(getApplicationContext());

        Button button = findViewById(R.id.button_addMeal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holdInfo.getIngredientModelList().size()>1){
                    ComplexProductModel cpm = new ComplexProductModel("meal "+holdInfo);
                    cpm.setIngredients(holdInfo.getIngredientModelList());
                    MealModel mealModel = new MealModel(Calendar.getInstance(), cpm, 21.37);
                    userRepository.insertMeal(mealModel);
                }else if(holdInfo.getIngredientModelList().size() == 1){
                    SimpleProductModel sp = new SimpleProductModel("meal "+holdInfo,
                            holdInfo.getIngredientModelList().get(0).getProduct().getNutrients());

                    MealModel mealModel = new MealModel(Calendar.getInstance(), sp, 21.37);
                    userRepository.insertMeal(mealModel);
                }
                holdInfo.setMealNr(holdInfo.getMealNr()+1);
                Intent i = new Intent(AddedProductsActivity.this, DayActivity.class);
                startActivity(i);
            }
        });
    }

    public void initList(){
        apa = new AddedProductsAdapter(this, holdInfo.getIngredientModelList());
        final ListView listView = findViewById(R.id.list_added);
        listView.setAdapter(apa);
    }
}
