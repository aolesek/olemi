package pl.edu.agh.student.olemi;

import android.os.Bundle;
import android.widget.TextView;

import com.google.common.collect.Lists;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.threeten.bp.LocalDate;

import java.util.LinkedList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import pl.edu.agh.student.olemi.repositories.NoDbUserRepository;
import pl.edu.agh.student.olemi.repositories.UserRepository;

public class StatsActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.stats_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle(getString(R.string.stats_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userRepository = new NoDbUserRepository(getApplicationContext(), true);

        userRepository.getCaloriesGoalStats(7).subscribe(goals -> fillStats(goals, R.id.statsWeekCalories, R.id.statsWeekDaily, R.id.statsWeekDeviation, R.id.week_graph));
        userRepository.getCaloriesGoalStats(21).subscribe(goals -> fillStats(goals, R.id.statsMonthCalories, R.id.statsMonthDaily, R.id.statsMonthDeviation, R.id.month_graph));

    }

    private void fillStats(List<Pair<Double, Integer>> calorieGoals, int totalId, int dailyId, int deviationId, int graphId) {
        final int days = calorieGoals.size();
        final double eatenCalories = calorieGoals.stream()
                .map(pair -> pair.first)
                .mapToDouble(Double::doubleValue).sum();
        final int maxAllowedCalories = calorieGoals.stream()
                .map(pair -> pair.second)
                .mapToInt(Integer::intValue).sum();
        final double deviation = ((maxAllowedCalories - eatenCalories) / days);
        ((TextView) findViewById(totalId)).setText(String.format(getString(R.string.statsWeekCaloriesTotal),
                eatenCalories, maxAllowedCalories));
        final double eatenDaily = (eatenCalories / days);
        final int allowedDaily = (int) (maxAllowedCalories / days);
        ((TextView) findViewById(dailyId)).setText(String.format(getString(R.string.statsWeekCaloriesDaily), eatenDaily, allowedDaily));
        ((TextView) findViewById(deviationId)).setText(String.format(getString(R.string.statsWeekCaloriesVariation), deviation));
        fillGraph(calorieGoals, graphId);
    }

    private void fillGraph(List<Pair<Double, Integer>> calorieGoals, int graphId) {
        final List<Pair<Double, Integer>> reversed = Lists.reverse(calorieGoals); // calorie
        // goals are from the latest by default
        final List<DataPoint> dataPoints = new LinkedList<>();

        for (int i = 0; i < reversed.size(); i++) {
            dataPoints.add(new DataPoint(i, reversed.get(i).first));
        }

        final GraphView graph = (GraphView) findViewById(graphId);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints.toArray(new DataPoint[0]));
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    int val = (int) value;
                    return xToDay(val, reversed.size());
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        graph.addSeries(series);
    }

    private String xToDay(int x, int numberOfDays) {
        final LocalDate day = LocalDate.now().minusDays(numberOfDays - x - 1);
        return String.valueOf(day.getDayOfMonth());
    }
}
