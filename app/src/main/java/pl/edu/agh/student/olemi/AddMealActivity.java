package pl.edu.agh.student.olemi;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.helpers.MealAdapter;
import pl.edu.agh.student.olemi.helpers.ViewGroupUtils;
import pl.edu.agh.student.olemi.model.Nutrients;
import pl.edu.agh.student.olemi.model.ProductModel;
import pl.edu.agh.student.olemi.model.SimpleProduct;

public class AddMealActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {


    private SearchView searchView;
    private MenuItem searchMenuItem;
    private MealAdapter mealAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);


        initList();
        setActionBar();
    }

    public void initList(){
        final List<ProductModel> productModels = new ArrayList<>();
        productModels.add(new SimpleProduct("jajko", new Nutrients(2.137, 2.137, 2.137, 2.137)));
        productModels.add(new SimpleProduct("bozena", new Nutrients(2.1, 2.137, 2.137, 2.137)));
        productModels.add(new SimpleProduct("abc", new Nutrients(1.1, 2.137, 2.137, 2.137)));
        mealAdapter = new MealAdapter(this, productModels);
        final ListView listView = findViewById(R.id.list_new_meal_search);
        listView.setAdapter(mealAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText list_meal_info_carbs = (EditText) view.findViewById(R.id.editText);
                list_meal_info_carbs.setVisibility(View.VISIBLE);



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

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
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

