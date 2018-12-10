package pl.edu.agh.student.olemi.repositories;

import java.util.List;

import androidx.core.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

public interface UserRepository {

    Completable insertMeal(MealModel mealModel);

    Completable insertUserData(UserDataModel userDataModel);

    Flowable<List<MealModel>> getMeals(String dayTimestamp);

    Flowable<List<MealModel>> getMealsForLastDays(int numberOfDays);

    Single<UserDataModel> getUserData();

    Completable setUserData(UserDataModel userData);

    Single<Pair<Nutrients, UserDataModel>> getFullGoalStats(String dayTimestamp);

    Maybe<Pair<Double, Integer>> getCaloriesGoalStats(String dayTimestamp);

    Single<List<Pair<Double, Integer>>> getCaloriesGoalStats(int numberOfDays);
}
