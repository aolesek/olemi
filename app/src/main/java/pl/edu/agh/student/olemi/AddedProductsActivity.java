package pl.edu.agh.student.olemi;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import pl.edu.agh.student.olemi.helpers.AddedProductsAdapter;
import pl.edu.agh.student.olemi.helpers.HoldInfo;
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
                onButtonShowPopupWindowClick(v);

                /*TODO
                dodanie ingriendtów
                usuwanie poszczegónych składników
                 */
            }
        });

        Button buttonDeleteMeal  = findViewById(R.id.button_deleteMeal);
        buttonDeleteMeal.setOnClickListener(v -> {
            holdInfo.getIngredientModelList().clear();
            Intent i = new Intent(AddedProductsActivity.this, DayActivity.class);
            startActivity(i);
        });
    }

    private void addMeal(String mealName, Double mealWeight){
        if(holdInfo.getIngredientModelList().size()>1){
            ComplexProductModel cpm = new ComplexProductModel(mealName);
            cpm.setIngredients(holdInfo.getIngredientModelList());
            MealModel mealModel = new MealModel(Calendar.getInstance(), cpm, mealWeight);
            userRepository.insertMeal(mealModel).subscribe();
        }else if(holdInfo.getIngredientModelList().size() == 1){
            SimpleProductModel sp = new SimpleProductModel(mealName,
                    holdInfo.getIngredientModelList().get(0).getProduct().getNutrients());

            MealModel mealModel = new MealModel(Calendar.getInstance(), sp, mealWeight);
            userRepository.insertMeal(mealModel).subscribe();
        }
        holdInfo.setMealNr(holdInfo.getMealNr()+1);
        holdInfo.getIngredientModelList().clear();
        Intent i = new Intent(AddedProductsActivity.this, DayActivity.class);
        startActivity(i);
    }

    public void initList(){
        apa = new AddedProductsAdapter(this, holdInfo.getIngredientModelList());
        final ListView listView = findViewById(R.id.list_added);
        listView.setAdapter(apa);
    }


    public void onButtonShowPopupWindowClick(View view) {


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_add_meal, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);


        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // set product name
        View contentView = popupWindow.getContentView();

        Button dismissButton = contentView.findViewById(R.id.button_dissmiss_popup);
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        final Button addButton = contentView.findViewById(R.id.button_add_popup);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_mealName = contentView.findViewById(R.id.editText_mealName);
                EditText editText_mealWeight = contentView.findViewById(R.id.editText_mealWeight);
                String mealName = editText_mealName.getText().toString();
                Double mealWeight = Double.parseDouble(editText_mealWeight.getText().toString());
                addMeal(mealName, mealWeight);
                popupWindow.dismiss();
            }
        });


        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        dimBehind(popupWindow);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
}
