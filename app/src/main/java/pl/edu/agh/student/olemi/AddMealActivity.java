package pl.edu.agh.student.olemi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.helpers.MealAdapter;
import pl.edu.agh.student.olemi.helpers.SearchMealAdapter;
import pl.edu.agh.student.olemi.helpers.ViewGroupUtils;
import pl.edu.agh.student.olemi.model.ComplexProduct;
import pl.edu.agh.student.olemi.model.Nutrients;
import pl.edu.agh.student.olemi.model.Pair;
import pl.edu.agh.student.olemi.model.ProductModel;
import pl.edu.agh.student.olemi.model.SimpleProduct;

public class AddMealActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchView searchView;
    private MenuItem searchMenuItem;
    private MenuItem addedMenuItem;
    private SearchMealAdapter mealAdapter;
    private List<ProductModel> productModels;
    private ArrayList<Pair> ingriedients = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        initList();
        setActionBar();
    }

    public void initList(){
        productModels = new ArrayList<>();
        productModels.add(new SimpleProduct("jajko", new Nutrients(2.137, 2.137, 2.137, 2.137)));
        productModels.add(new SimpleProduct("bozena", new Nutrients(2.1, 2.137, 2.137, 2.137)));
        productModels.add(new SimpleProduct("abc", new Nutrients(1.1, 2.137, 2.137, 2.137)));
        mealAdapter = new SearchMealAdapter(this, productModels);
        final ListView listView = findViewById(R.id.list_new_meal_search);
        listView.setAdapter(mealAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProductModel productModel = productModels.get(position);
                onButtonShowPopupWindowClick(view, productModel);
            }
        });
    }

    public void onButtonShowPopupWindowClick(View view, final ProductModel productModel) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

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
        TextView productName =  contentView.findViewById(R.id.popup_product_name);
        productName.setText(productModel.getName());

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
                ingriedients.add(new Pair(2.137, productModel));
                Toast.makeText(getApplicationContext(), productModel.getName()+" added", Toast.LENGTH_SHORT).show();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        addedMenuItem = menu.findItem(R.id.added_button);
        addedMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(AddMealActivity.this, AddedProductsActivity.class);
                startActivity(i);
                return true;
            }
        });

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        //searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mealAdapter.getFilter().filter(newText);
        return true;
    }

    private void setActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Friends");

//        Typeface typeface = Typeface..createFromAsset(this.getAssets(), "fonts/vegur_2.otf");
//        int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
//        TextView actionBarTitle = (TextView) (this.findViewById(titleId));
//       // actionBarTitle.setTextColor(getResources().getColor(R.color.white));
//        actionBarTitle.setTypeface(typeface);
    }
}

