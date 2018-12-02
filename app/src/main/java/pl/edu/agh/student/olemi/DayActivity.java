package pl.edu.agh.student.olemi;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class DayActivity extends AppCompatActivity {

    public static final String SELECTED_DAY = "pl.edu.agh.student.olemi.day.SELECTED_DAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day);
    }
}
