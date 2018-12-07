package pl.edu.agh.student.olemi.sampledata;

import android.content.Context;
import android.icu.util.Calendar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import androidx.annotation.IntRange;
import pl.edu.agh.student.olemi.models.ComplexProductModel;
import pl.edu.agh.student.olemi.models.IngredientModel;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.models.UserDataModel;
import pl.edu.agh.student.olemi.repositories.ProductRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import timber.log.Timber;

public class ExampleData {

    private final List<ProductModel> availableProducts = new ArrayList<>();

    private final ListMultimap<Calendar, MealModel> meals = ArrayListMultimap.create();

    private final UserDataModel userData;

    private Context context;

    public ExampleData(Context context) {
        this.context = context;
        generateSimpleProducts();
        generateComplexProducts();

        this.userData = UserDataModel.builder()
                .age(21)
                .activityLevel(2)
                .weight(80)
                .height(180)
                .weightLossRate(0.5)
                .gender("male").build();


        generateMeals();
    }

    public void persistGeneratedData(ProductRepository productRepository, UserRepository userRepository) {
        availableProducts.forEach(product -> productRepository.insertProduct(product).subscribe());
        meals.values().forEach(meal -> userRepository.insertMeal(meal).subscribe());
        userRepository.insertUserData(userData).subscribe();
        Timber.i("generated data persisted");
    }

    private void generateSimpleProducts() {
        final String simpleProductsJSON = loadJSONFromAsset("simpleProducts.json");
        final SimpleProductModel[] simpleProducts = new Gson().fromJson(simpleProductsJSON, SimpleProductModel[].class);
        availableProducts.addAll(Arrays.asList(simpleProducts));
    }

    private void generateComplexProducts() {
        final ComplexProductModel p1 = new ComplexProductModel("Tosty z serem");
        p1.addIngredient(new IngredientModel(0.5, availableProducts.get(0)));
        p1.addIngredient(new IngredientModel(0.3, availableProducts.get(1)));
        p1.addIngredient(new IngredientModel(0.2, availableProducts.get(2)));

        final ComplexProductModel p2 = new ComplexProductModel("Jajecznica");
        p2.addIngredient(new IngredientModel(0.6, availableProducts.get(3)));
        p2.addIngredient(new IngredientModel(0.1, availableProducts.get(4)));
        p2.addIngredient(new IngredientModel(0.3, availableProducts.get(5)));

        final ComplexProductModel p3 = new ComplexProductModel("Spaghetti");
        p3.addIngredient(new IngredientModel(0.6, availableProducts.get(6)));
        p3.addIngredient(new IngredientModel(0.1, availableProducts.get(7)));
        p3.addIngredient(new IngredientModel(0.3, availableProducts.get(8)));

        final ComplexProductModel p4 = new ComplexProductModel("Ziemniaki z kotletem schabowym");
        p4.addIngredient(new IngredientModel(0.6, availableProducts.get(9)));
        p4.addIngredient(new IngredientModel(0.1, availableProducts.get(10)));
        p4.addIngredient(new IngredientModel(0.3, p3));

        availableProducts.addAll(Arrays.asList(p1, p2, p3, p4));
    }

    private void generateMeals() {
        Random r = new Random();


        for (int i = 0; i < 30; i++) {
            int numberOfMeals = r.nextInt(5) + 1;
            Calendar day = Calendar.getInstance();
            day.add(Calendar.DATE, -i);
            for (int j = 0; j < numberOfMeals; j++) {
                double weight = (r.nextInt(500) + 100);
                meals.put(day, new MealModel(day, getRandomProduct(), weight));
            }
        }
    }

    private ProductModel getRandomProduct() {
        Random r = new Random();
        int productNumber = r.nextInt(availableProducts.size() - 1);
        return availableProducts.get(productNumber);
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
