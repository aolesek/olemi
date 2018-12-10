package pl.edu.agh.student.olemi.repositories;

import android.content.Context;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import androidx.core.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import pl.edu.agh.student.olemi.database.MockDatabase;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

public class NoDbUserRepository implements UserRepository {

    final MockDatabase mockDatabase;

    public NoDbUserRepository(Context context, boolean shoudGenerateData) {
        if (shoudGenerateData) {
            this.mockDatabase = MockDatabase.getInstance(context);
        } else {
            this.mockDatabase = MockDatabase.getEmptyInstance(context);
        }
    }

    @Override
    public Completable insertMeal(MealModel mealModel) {
        return Completable.fromRunnable(() -> mockDatabase.meals.put(mealModel.getDay().toString(),
                mealModel));
    }

    @Override
    public Completable insertUserData(UserDataModel userDataModel) {
        return Completable.fromRunnable(() -> mockDatabase.userData = userDataModel);
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
            final String dateAsString = LocalDate.now().minusDays(dayNumber).toString();
            filteredMeals.addAll(mockDatabase.meals.get(dateAsString));
        });

        return Flowable.just(filteredMeals);
    }

    @Override
    public Single<UserDataModel> getUserData() {
        return Single.just(mockDatabase.userData);
    }

    @Override
    public Completable setUserData(UserDataModel userData) {
        return Completable.fromRunnable(() -> mockDatabase.userData = userData);
    }

    @Override
    public Single<Pair<Nutrients, UserDataModel>> getFullGoalStats(String day) {
        final Set<Nutrients> allDayNutrients = mockDatabase.meals.get(day).stream()
                .map(mealModel -> {
                    return mealModel.getNutrients();
                })
                .collect(Collectors.toSet());
        final Nutrients sumOfNutrients = Nutrients.sumOf(allDayNutrients.toArray(new Nutrients[0]));
        return Single.just(Pair.create(sumOfNutrients, mockDatabase.userData));
    }

    @Override
    public Maybe<Pair<Double, Integer>> getCaloriesGoalStats(String day) {
        return getFullGoalStats(day)
                .filter(dataWithNutrients -> Objects.nonNull(dataWithNutrients.first) && Objects.nonNull(dataWithNutrients.second))
                .map(dataWithNutrients -> Pair.create(dataWithNutrients.first.calories, dataWithNutrients.second.getCaloriesGoal()));
    }

    @Override
    public Single<List<Pair<Double, Integer>>> getCaloriesGoalStats(int numberOfDays) {
        final List<String> days = new LinkedList<>();
        IntStream.range(0, numberOfDays).forEach(dayNumber -> {
            final String day = LocalDate.now().minusDays(dayNumber).toString();
            days.add(day);
        });

        final Single<List<Pair<Double, Integer>>> results = Flowable.fromIterable(days)
                .flatMapMaybe(this::getCaloriesGoalStats)
                .toList();
        return results;
    }
}
