package pl.edu.agh.student.olemi.repositories;

import android.content.Context;
import android.icu.util.Calendar;

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
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

import static pl.edu.agh.student.olemi.utils.DateTimeUtils.calendarDateToString;
import static pl.edu.agh.student.olemi.utils.DateTimeUtils.clearCalendar;

public class NoDbUserRepository implements UserRepository {

    MockDatabase mockDatabase = MockDatabase.getInstance();

    public NoDbUserRepository(Context context) {}

    @Override
    public Completable insertMeal(MealModel mealModel) {
        return Completable.fromRunnable(() -> mockDatabase.meals.put(calendarDateToString(mealModel.getDay()), mealModel));
    }

    @Override
    public Completable insertUserData(UserDataModel userDataModel) {
        return Completable.fromRunnable(() -> mockDatabase.userData = userDataModel);
    }

    @Override
    public Flowable<List<MealModel>> getMeals(Calendar day) {
        final Collection<MealModel> mealModels = mockDatabase.meals.get(calendarDateToString(day));
        return Flowable.just(new ArrayList<>(mealModels));
    }

    @Override
    public Flowable<List<MealModel>> getMeals(String dayTimestamp) {
        final Collection<MealModel> mealModels = mockDatabase.meals.get(dayTimestamp);
        return Flowable.just(new ArrayList<>(mealModels));
    }

    @Override
    public Flowable<List<MealModel>> getMealsForLastDays(int numberOfDays) {
        final List<MealModel> filteredMeals = new ArrayList<>();
        IntStream.range(0, numberOfDays).forEach(dayNumber -> {
            final Calendar day = Calendar.getInstance();
            day.add(Calendar.DAY_OF_YEAR, -dayNumber);
            clearCalendar(day);
            filteredMeals.addAll(mockDatabase.meals.get(calendarDateToString(day)));
        });

        return Flowable.just(filteredMeals);
    }

    @Override
    public Single<UserDataModel> getUserData() {
        return Single.just(mockDatabase.userData);
    }

    @Override
    public Single<Pair<Nutrients, UserDataModel>> getFullGoalStats(Calendar day) {
        clearCalendar(day);
        final Set<Nutrients> allDayNutrients = mockDatabase.meals.get(calendarDateToString(day)).stream()
                .map(mealModel -> {
                    final Nutrients nutrients = mealModel.getProduct().getNutrients();
                    final Double weight = mealModel.getWeight();
                    nutrients.multiplyBy(weight);
                    return nutrients;
                })
                .collect(Collectors.toSet());
        final Nutrients sumOfNutrients = Nutrients.sumOf(allDayNutrients.toArray(new Nutrients[0]));
        return Single.just(Pair.create(sumOfNutrients, mockDatabase.userData));
    }

    @Override
    public Single<Pair<Integer, Integer>> getCaloriesGoalStats(Calendar day) {
        clearCalendar(day);
        return getFullGoalStats(day).map(
                dataWithNutrients -> Pair.create(dataWithNutrients.first.calories, dataWithNutrients.second.getCaloriesGoal()));
    }
}
