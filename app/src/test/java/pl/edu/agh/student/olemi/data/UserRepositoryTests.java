package pl.edu.agh.student.olemi.data;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.threeten.bp.LocalDate;

import java.util.Arrays;
import java.util.List;

import androidx.core.util.Pair;
import pl.edu.agh.student.olemi.database.MockDatabase;
import pl.edu.agh.student.olemi.entities.Nutrients;
import pl.edu.agh.student.olemi.entities.NutrientsBuilder;
import pl.edu.agh.student.olemi.models.MealModel;
import pl.edu.agh.student.olemi.models.SimpleProductModel;
import pl.edu.agh.student.olemi.models.UserDataModel;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class UserRepositoryTests {

    private UserRepository repository;

    private Context context;

    private LocalDate today, yesterday, dayBeforeYesterday;

    SimpleProductModel exampleProduct1;
    SimpleProductModel exampleProduct2;
    SimpleProductModel exampleProduct3;
    SimpleProductModel exampleProduct4;
    SimpleProductModel exampleProduct5;
    SimpleProductModel exampleProduct6;

    MealModel t1;
    MealModel t2;
    MealModel y1;
    MealModel y2;
    MealModel by1;
    MealModel by2;

    @Before
    public void prepare() {
        context = mock(Context.class);
        repository = new NoDbUserRepository(context, false);
        MockDatabase.reset();

        today = LocalDate.now();
        yesterday = LocalDate.now().minusDays(1);
        dayBeforeYesterday = LocalDate.now().minusDays(2);

        exampleProduct1 = new SimpleProductModel("example product 1",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
        exampleProduct2 = new SimpleProductModel("example product 2",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
        exampleProduct3 = new SimpleProductModel("example product 3",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
        exampleProduct4 = new SimpleProductModel("example product 4",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
        exampleProduct5 = new SimpleProductModel("example product 5",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());
        exampleProduct6 = new SimpleProductModel("example product 6",
                NutrientsBuilder.aNutrients().withCalories(10d)
                        .withCarbohydrates(20d).withFats(30d).withProtein(40d).build());

        t1 = new MealModel(today, exampleProduct1, 1000d);
        t2 = new MealModel(today, exampleProduct2, 2000d);
        y1 = new MealModel(yesterday, exampleProduct3, 3000d);
        y2 = new MealModel(yesterday, exampleProduct4, 4000d);
        by1 = new MealModel(dayBeforeYesterday, exampleProduct5, 5000d);
        by2 = new MealModel(dayBeforeYesterday, exampleProduct6, 6000d);
    }

    @Test
    public void userDataTest() {
        // given
        final UserDataModel userData = UserDataModel.builder().gender("male")
                .weightLossRate(0.1)
                .weight(100)
                .height(100)
                .activityLevel(2)
                .age(19)
                .build();

        //when
        repository.insertUserData(userData).blockingGet();
        final UserDataModel dataFromDb = repository.getUserData().blockingGet();

        // then
        assertEquals("User data from db should be same as inserted", userData, dataFromDb);
    }

    @Test
    public void noMealsTest() {
        // given

        // when
        final List<MealModel> meals = repository.getMeals(today.toString()).blockingFirst();
        final List<MealModel> stringMeals = repository.getMeals(today.toString()).blockingFirst();
        final List<MealModel> last100daysMeals = repository.getMealsForLastDays(100)
                .blockingFirst();

        // then
        assertEquals("Meals for today should be empty", 0, meals.size());
        assertEquals("Meals for today should be empty", 0, stringMeals.size());
        assertEquals("Meals for last 100 days should be empty", 0, last100daysMeals.size());
    }

    @Test
    public void singleMealTest() {
        // given
        MockDatabase.reset();
        // when
        repository.insertMeal(t1).blockingGet();
        final List<MealModel> todaysMeals = repository.getMeals(today.toString()).blockingFirst();

        // then
        assertEquals("Should retunr single meal for today" + todaysMeals, 1, todaysMeals.size());
        assertEquals("Returned meal should be same as inserted", t1, todaysMeals.iterator().next());
    }

    @Test
    public void multipleMealsTest() {
        // given
        repository.insertMeal(t1).blockingGet();
        repository.insertMeal(t2).blockingGet();
        repository.insertMeal(y1).blockingGet();
        repository.insertMeal(y2).blockingGet();
        repository.insertMeal(by1).blockingGet();
        repository.insertMeal(by2).blockingGet();

        // when
        final List<MealModel> todaysMeals = repository.getMeals(today.toString()).blockingFirst();

        final List<MealModel> yesterdayMeals = repository.getMeals(yesterday.toString()).blockingFirst();

        final List<MealModel> beforeYesterdayMeals = repository.getMeals(dayBeforeYesterday.toString())
                .blockingFirst();

        final List<MealModel> last1dayMeals = repository.getMealsForLastDays(1).blockingFirst();
        final List<MealModel> last2daysMeals = repository.getMealsForLastDays(2).blockingFirst();
        final List<MealModel> last3daysMeals = repository.getMealsForLastDays(3).blockingFirst();


        // then
        assertEquals("Should return 2 meals for today", 2, todaysMeals.size());
        assertTrue("Returned meals should be same as inserted", todaysMeals.containsAll(Arrays
                .asList(t1, t2)));

        assertEquals("Should return 2 meals for yesterday", 2, yesterdayMeals.size());
        assertTrue("Returned meals should be same as inserted", yesterdayMeals.containsAll(Arrays
                .asList(y1, y2)));

        assertEquals("Should return 2 meals for day before yesterday", 2, beforeYesterdayMeals.size());
        assertTrue("Returned meals should be same as inserted", beforeYesterdayMeals.containsAll(Arrays
                .asList(by1, by2)));

        assertEquals("Should return meals for last day", 2, last1dayMeals.size());
        assertEquals("Should return meals for last days", 4, last2daysMeals.size());
        assertEquals("Should return meals for last days", 6, last3daysMeals.size());
    }

    @Test
    public void fullGoalStatsTests() {
        // given
        repository.insertMeal(t1).blockingGet();
        repository.insertMeal(t2).blockingGet();
        repository.insertMeal(y1).blockingGet();
        repository.insertMeal(y2).blockingGet();
        repository.insertMeal(by1).blockingGet();
        repository.insertMeal(by2).blockingGet();

        // when
        final Pair<Nutrients, UserDataModel> todaysStats = repository.getFullGoalStats(today.toString())
                .blockingGet();

        final Nutrients singleNutrients = NutrientsBuilder.aNutrients().withCalories(10d)
                .withCarbohydrates(20d).withFats(30d).withProtein(40d).build();
        final Nutrients product1Nutrients = singleNutrients.multiplyBy(10d);
        final Nutrients product2Nutrients = singleNutrients.multiplyBy(20d);

        final Nutrients sumOfNutrients = Nutrients.sumOf(product1Nutrients, product2Nutrients);

        final Pair<Double, Integer> caloriesGoal = repository.getCaloriesGoalStats(today.toString())
                .blockingGet();

        final List<Pair<Double, Integer>> statsFor3Days = repository.getCaloriesGoalStats(3).blockingGet();
        final long nonZeroCount = statsFor3Days.stream().map(pair -> pair.first).filter(calories -> calories > 0.001).count
                ();

        // then
        assertEquals("Nutrients should be equals", sumOfNutrients, todaysStats.first);
        assertEquals("Calories goal should be same on full and simplified stats", todaysStats
                .first.calories, caloriesGoal.first);
        assertEquals("Stats for last 3 days should have 3 entries", 3, statsFor3Days.size());
        assertEquals("Non zero calories stats for last 3 days should be 3", 3, nonZeroCount);
    }
}
