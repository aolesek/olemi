package pl.edu.agh.student.olemi.repositories;

import android.content.Context;
import android.icu.util.Calendar;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import androidx.core.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import pl.edu.agh.student.olemi.database.MockDatabase;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.Product;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

public class NoDbUserRepository implements UserRepository {

    MockDatabase mockDatabase = MockDatabase.getInstance();

    public NoDbUserRepository(Context context) {}

    @Override
    public Completable insertMeal(MealModel mealModel) {
        return Completable.fromRunnable(() -> mockDatabase.meals.put(mealModel.getDay(), mealModel));
    }

    @Override
    public Flowable<List<MealModel>> getMeals(Calendar day) {
        final Collection<MealModel> mealModels = mockDatabase.meals.get(day);
        return Flowable.just(new ArrayList<>(mealModels));
    }

    @Override
    public Flowable<List<MealModel>> getMealsForLastDays(int numberOfDays) {
        final List<MealModel> filteredMeals = new ArrayList<>();
        IntStream.range(0, numberOfDays).forEach(dayNumber -> {
            final Calendar day = Calendar.getInstance();
            day.add(Calendar.DAY_OF_YEAR, -dayNumber);
            filteredMeals.addAll(mockDatabase.meals.get(day));
        });

        return Flowable.just(filteredMeals);
    }

    @Override
    public Single<UserDataModel> getUserData() {
        return Single.just(mockDatabase.userData);
    }

    @Override
    public Single<Pair<Nutrients, UserDataModel>> getFullGoalStats(Calendar day) {
        final Set<Nutrients> allDayNutrients = mockDatabase.meals.get(day).stream()
                .map(MealModel::getProduct)
                .map(Product::getNutrients)
                .collect(Collectors.toSet());
        final Nutrients sumOfNutrients = Nutrients.sumOf(allDayNutrients.toArray(new Nutrients[0]));
        return Single.just(Pair.create(sumOfNutrients, mockDatabase.userData));
    }

    @Override
    public Single<Pair<Integer, Integer>> getCaloriesGoalStats(Calendar day) {
        return getFullGoalStats(day).map(
                dataWithNutrients -> Pair.create(dataWithNutrients.first.calories, dataWithNutrients.second.getCaloriesGoal()));
    }
}
