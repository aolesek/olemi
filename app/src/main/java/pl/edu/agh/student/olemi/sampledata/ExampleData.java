package pl.edu.agh.student.olemi.sampledata;

import android.content.Context;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.Gson;

import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import pl.edu.agh.student.olemi.entities.Nutrients;
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

    private final int NUMBER_OF_DAYS = 30;

    private final List<ProductModel> availableProducts = new ArrayList<>();

    private final ListMultimap<LocalDate, MealModel> meals = ArrayListMultimap.create();

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
        for (int i = 0; i < NUMBER_OF_DAYS; i++) {
            LocalDate day = LocalDate.now().minusDays(i);
            generateMealsForSingleDay(day);
        }
    }

    private void generateMealsForSingleDay(LocalDate day) {
        Random r = new Random();

        while (!isNearGoal(day)) {
            double weight = (r.nextInt(100) + 200);
            final MealModel mealModel = new MealModel(day, getRandomProduct(), weight);
            meals.put(day, mealModel);

        }
    }

    private boolean isNearGoal(LocalDate day) {
        final List<MealModel> mealModels = meals.get(day);
        final List<Nutrients> allMealsNutrients = mealModels.stream()
                .map(MealModel::getNutrients)
                .collect(Collectors.toList());

        final Nutrients sum = Nutrients.sumOf(allMealsNutrients.toArray(new Nutrients[0]));
        if (Math.abs(sum.calories - userData.getCaloriesGoal()) < 300 || sum.calories - userData.getCaloriesGoal() > 300) {
//            Timber.i("Near goal! " + sum.calories);
            return true;
        }
        return false;
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
        } catch (NullPointerException e) {
            return "[{\"name\":\"Jajko\",\"nutrients\":{\"calories\":120,\"carbohydrates\":50,\"fats\":12,\"protein\":13}},{\"name\":\"Ser\",\"nutrients\":{\"calories\":45,\"carbohydrates\":12,\"fats\":34,\"protein\":12}},{\"name\":\"Masło\",\"nutrients\":{\"calories\":400,\"carbohydrates\":4,\"fats\":14,\"protein\":5}},{\"name\":\"Pomidor\",\"nutrients\":{\"calories\":32,\"carbohydrates\":50,\"fats\":1,\"protein\":1}},{\"name\":\"Ogórek\",\"nutrients\":{\"calories\":16,\"carbohydrates\":23,\"fats\":1,\"protein\":1}},{\"name\":\"Pierś z kurczaka surowa\",\"nutrients\":{\"calories\":123,\"carbohydrates\":12,\"fats\":14,\"protein\":30}},{\"name\":\"Musztarda\",\"nutrients\":{\"calories\":78,\"carbohydrates\":23,\"fats\":12,\"protein\":1}},{\"name\":\"Chleb\",\"nutrients\":{\"calories\":200,\"carbohydrates\":34,\"fats\":3,\"protein\":1}},{\"name\":\"Kasza jaglana\",\"nutrients\":{\"calories\":80,\"carbohydrates\":64,\"fats\":1,\"protein\":0}},{\"name\":\"Kinder niespodzianka\",\"nutrients\":{\"calories\":300,\"carbohydrates\":50,\"fats\":20,\"protein\":0}},{\"name\":\"CocaCola Zero\",\"nutrients\":{\"calories\":0,\"carbohydrates\":3,\"fats\":0,\"protein\":0}},{\"name\":\"Ciasteczka z kokainą\",\"nutrients\":{\"calories\":12,\"carbohydrates\":34,\"fats\":56,\"protein\":78}},{\"name\":\"Makaron Penne\",\"nutrients\":{\"calories\":120,\"carbohydrates\":33,\"fats\":11,\"protein\":2}}]\n";
        }
        return json;
    }
}
