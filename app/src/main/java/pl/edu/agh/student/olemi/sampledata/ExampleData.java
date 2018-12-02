package pl.edu.agh.student.olemi.sampledata;

import android.content.Context;
import android.icu.util.Calendar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

public class ExampleData {

    private final List<ProductModel> availableProducts = new ArrayList<>();

    private final ListMultimap<Calendar, MealModel> meals = ArrayListMultimap.create();

    private final UserDataModel userData = new UserDataModel();

    private Context context;

    public ExampleData(Context context) {
        this.context = context;
        generateSimpleProducts();
        generateComplexProducts();
    }

    private void generateSimpleProducts() {
        String simpleProductsJSON = loadJSONFromAsset("simpleProducts.json");
        List<SimpleProductModel> simpleProducts = new Gson().fromJson(simpleProductsJSON, new ArrayList<SimpleProductModel>().getClass());
        availableProducts.addAll(simpleProducts);
    }

    private void generateComplexProducts() {

        //TODO: implement
    }

    private String loadJSONFromAsset(String jsonName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(jsonName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
