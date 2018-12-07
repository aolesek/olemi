package pl.edu.agh.student.olemi.repositories;

import android.icu.util.Calendar;

import java.util.List;

import androidx.core.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.UserDataModel;

public interface UserRepository {

    Completable insertMeal(MealModel mealModel);

    Completable insertUserData(UserDataModel userDataModel);

    Flowable<List<MealModel>> getMeals(Calendar day);

    Flowable<List<MealModel>> getMeals(String dayTimestamp);

    Flowable<List<MealModel>> getMealsForLastDays(int numberOfDays);

    Single<UserDataModel> getUserData();

    Completable setUserData(UserDataModel userData);

    Single<Pair<Nutrients, UserDataModel>> getFullGoalStats(Calendar day);

    Single<Pair<Integer, Integer>> getCaloriesGoalStats(Calendar day);

    Single<List<Pair<Integer, Integer>>> getCaloriesGoalStats(int numberOfDays);
}
