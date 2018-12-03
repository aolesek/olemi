package pl.edu.agh.student.olemi.database;

import android.icu.util.Calendar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

public class MockDatabase {

    public final List<ProductModel> availableProducts = new ArrayList<>();

    public final ListMultimap<String, MealModel> meals = ArrayListMultimap.create();

    public UserDataModel userData = new UserDataModel();

    private static final MockDatabase instance = new MockDatabase();

    private MockDatabase() {}

    public static MockDatabase getInstance() {
        return instance;
    }
}
