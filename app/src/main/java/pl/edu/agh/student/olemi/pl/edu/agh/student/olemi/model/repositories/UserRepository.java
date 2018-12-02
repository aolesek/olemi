package pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.repositories;

import android.icu.util.Calendar;

import java.util.List;

import androidx.core.util.Pair;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.entities.Nutrients;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models.MealModel;
import pl.edu.agh.student.olemi.pl.edu.agh.student.olemi.model.models.UserDataModel;

public interface UserRepository {

    Completable insertMeal(MealModel mealModel);

    Flowable<List<MealModel>> getMeals(Calendar day);

    Flowable<List<MealModel>> getMealsForLastDays(int numberOfDays);

    Single<UserDataModel> getUserData();

    Single<Pair<Nutrients, UserDataModel>> getFullGoalStats(Calendar day);

    Single<Pair<Integer, Integer>> getCaloriesGoalStats(Calendar day);
}