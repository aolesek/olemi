package pl.edu.agh.student.olemi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import pl.edu.agh.student.olemi.helpers.AddedProductsAdapter;
import pl.edu.agh.student.olemi.helpers.HoldInfo;
import pl.edu.agh.student.olemi.helpers.SearchMealAdapter;
import pl.edu.agh.student.olemi.models.ComplexProductModel;

public class AddedProductsActivity extends AppCompatActivity {

    HoldInfo holdInfo = HoldInfo.getInstance();
    AddedProductsAdapter apa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_added_products);
        initList();

        Button button = findViewById(R.id.button_addMeal);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holdInfo.getIngredientModelList().size()>1){
                    ComplexProductModel cpm = new ComplexProductModel();
                    cpm.setIngredients(holdInfo.getIngredientModelList());
                    cpm.setName("meal "+holdInfo);

                }else {

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
