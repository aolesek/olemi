package pl.edu.agh.student.olemi.model;

import android.service.autofill.UserData;

import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.student.olemi.models.UserDataModel;

import static org.junit.Assert.assertEquals;

public class UserModelTests {

    private UserDataModel user1;
    private static int USER1_EXPECTED_CALORIE_GOAL;

    private UserDataModel user2;
    private static int USER2_EXPECTED_CALORIE_GOAL;

    @Before
    public void prepareData() {
        user1 = UserDataModel.builder()
                .activityLevel(2)
                .age(21)
                .gender("male")
                .height(160)
                .weight(70)
                .weightLossRate(0.5).build();
        USER1_EXPECTED_CALORIE_GOAL = 2480 // TDEE based on https://tdeecalculator.net/
                - (int) (user1.getWeightLossRate() * 1000);

        user2 = UserDataModel.builder()
                .activityLevel(1)
                .age(30)
                .gender("female")
                .height(180)
                .weight(60)
                .weightLossRate(0.2).build();
        USER2_EXPECTED_CALORIE_GOAL = 1944 // TDEE based on https://tdeecalculator.net/
                - (int)(user2.getWeightLossRate() * 1000);

    }

    @Test
    public void goalsTest() {
        // given

        // when

        //then
        assertEquals((long) user1.getCaloriesGoal(), USER1_EXPECTED_CALORIE_GOAL, 50);
        assertEquals((long) user1.getCarbonhydrateGoal(), (0.475 * USER1_EXPECTED_CALORIE_GOAL / 4), 10);
        assertEquals((long) user1.getFatGoal(), (0.37 * USER1_EXPECTED_CALORIE_GOAL / 9), 10);
        assertEquals((long) user1.getProteinGoal(), 2 * user1.getWeight(), 10);

        assertEquals(USER2_EXPECTED_CALORIE_GOAL, (long) user2.getCaloriesGoal(), 50);
        assertEquals((0.475 * USER2_EXPECTED_CALORIE_GOAL / 4), (long) user2.getCarbonhydrateGoal(), 10);
        assertEquals((0.37 * USER2_EXPECTED_CALORIE_GOAL / 9), (long) user2.getFatGoal(), 10);
        assertEquals(2 * user2.getWeight(), (long) user2.getProteinGoal(), 10);
    }
}
