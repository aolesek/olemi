package pl.edu.agh.student.olemi;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import pl.edu.agh.student.olemi.models.UserDataModel;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;

public class GoalSettings extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_settings);

        Toolbar goalToolbar = (Toolbar) findViewById(R.id.goal_settings_toolbar);
        setSupportActionBar(goalToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.goalsTitle));

        this.userRepository = new NoDbUserRepository(getApplicationContext(), true);
        userRepository.getUserData().subscribe(this::fillUserData);
    }

    private void fillUserData(UserDataModel userData) {

        ((TextView) findViewById(R.id.goalsAgeValue)).setText(String.valueOf(userData.getAge()));
        ((Spinner) findViewById(R.id.goalsActivitySpinner)).setSelection(userData.getActivityLevel());

        final String male = this.getResources().getStringArray(R.array.goalsGenders)[0];
        final int numberOnList = userData.getGender().equals(male) ? 0 : 1;
        ((Spinner) findViewById(R.id.goalsGenderSpinner)).setSelection(numberOnList);

        ((TextView) findViewById(R.id.goalsHeightValue)).setText(String.valueOf(userData.getHeight()));
        ((TextView) findViewById(R.id.goalsWeightValue)).setText(String.valueOf(userData.getWeight()));

        final int weightLossRateIndex = (int) (userData.getWeightLossRate() / 0.1 - 2);
        ((Spinner) findViewById(R.id.goalsWeightLossSpinner)).setSelection(weightLossRateIndex);

        ((TextView) findViewById(R.id.goalsCalorie)).setText(String.format(getString(R.string.goals_daily_calorie), userData.getCaloriesGoal()));
        ((TextView) findViewById(R.id.goalsFats)).setText(String.format(getString(R.string.goals_fats), userData.getFatGoal()));
        ((TextView) findViewById(R.id.goalsCarbs)).setText(String.format(getString(R.string.goals_carbs), userData.getCarbonhydrateGoal()));
        ((TextView) findViewById(R.id.goalsProteins)).setText(String.format(getString(R.string.goals_proteins), userData.getProteinGoal()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.goal_actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Context context = getApplicationContext();

        switch (item.getItemId()) {
            case R.id.goal_action_calculate:
                try {
                    UserDataModel userDataModel = UserDataModel.builder()
                            .activityLevel(getActivityLevel())
                            .age(getAge())
                            .gender(getGender())
                            .height(getHeight())
                            .weight(getWeight())
                            .weightLossRate(getWeightLossRate())
                            .build();

                    userRepository.setUserData(userDataModel).subscribe(() -> {
                        final String message = String.format(getString(R.string.goalsUserDataUpdated),
                                userDataModel.getCaloriesGoal(), userDataModel.getProteinGoal(),
                                userDataModel.getFatGoal(), userDataModel.getCarbonhydrateGoal());
                        Toast wtfToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                        wtfToast.show();
                        userRepository.getUserData().subscribe(this::fillUserData);
                    });
                } catch (NumberFormatException e) {
                    Toast wtfToast = Toast.makeText(context, getString(R.string.goalsUserDataError), Toast.LENGTH_SHORT);
                    wtfToast.show();
                }

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Double getWeightLossRate() {
        final int selectedItemPosition = ((Spinner) findViewById(R.id.goalsWeightLossSpinner)).getSelectedItemPosition();
        double firstRate = 0.1;
        double step = 0.1;
        double finalRate = firstRate + (selectedItemPosition + 1) * step;
        return finalRate;
    }

    private Integer getWeight() {
        final String weightAsString = ((TextView) findViewById(R.id.goalsWeightValue)).getText().toString();
        return Integer.valueOf(weightAsString);
    }

    private Integer getHeight() {
        final String heightAsString = ((TextView) findViewById(R.id.goalsHeightValue)).getText().toString();
        return Integer.valueOf(heightAsString);
    }

    private String getGender() {
        return ((Spinner) findViewById(R.id.goalsGenderSpinner)).getSelectedItem().toString();
    }

    private Integer getActivityLevel() {
        int selectedItemPosition = ((Spinner) findViewById(R.id.goalsActivitySpinner)).getSelectedItemPosition();
        if (selectedItemPosition < 0 && selectedItemPosition > 4) {
            throw new IllegalArgumentException();
        }
        return selectedItemPosition;
    }

    private Integer getAge() {
        return Integer.valueOf(((TextView) findViewById(R.id.goalsAgeValue)).getText().toString());
    }
}
