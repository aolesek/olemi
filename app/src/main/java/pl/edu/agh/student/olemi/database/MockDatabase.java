package pl.edu.agh.student.olemi.database;

import android.content.Context;
import android.icu.util.Calendar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.ProductModel;
import pl.edu.agh.student.olemi.models.UserDataModel;
import pl.edu.agh.student.olemi.repositories.NoDbProductRepository;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.ProductRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;
import pl.edu.agh.student.olemi.sampledata.ExampleData;
import timber.log.Timber;

public class MockDatabase {

    public final List<ProductModel> availableProducts = new ArrayList<>();

    public final ListMultimap<String, MealModel> meals = ArrayListMultimap.create();

    public UserDataModel userData = new UserDataModel();

    private static MockDatabase instance = null;

    private MockDatabase() {}

    public static MockDatabase getInstance(Context context) {
        if (Objects.isNull(instance)) {
            Timber.i("Generating new mock database");
            instance = new MockDatabase();
            ExampleData data = new ExampleData(context);
            ProductRepository productRepository = new NoDbProductRepository(context);
            UserRepository userRepository = new NoDbUserRepository(context);
            data.persistGeneratedData(productRepository, userRepository);
        }
        return instance;
    }
}
