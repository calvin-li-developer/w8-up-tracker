package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class WeightProgressChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_progress_chart);

        RadarChart weightRadarChart = findViewById(R.id.weightRadarChart);

        ArrayList<RadarEntry> legWeights = new ArrayList<>();
        legWeights.add(new RadarEntry(205)); //Back squat
        legWeights.add(new RadarEntry(155)); //Front squat
        legWeights.add(new RadarEntry(165)); //Calf raise
        legWeights.add(new RadarEntry(265)); //Leg press
        legWeights.add(new RadarEntry(205)); //Leg extension
        legWeights.add(new RadarEntry(205)); //Leg curl

        RadarDataSet legRadarDataSet = new RadarDataSet(legWeights, "Leg Workout Progress");
        legRadarDataSet.setColor(Color.DKGRAY);
        legRadarDataSet.setLineWidth(3f);
        legRadarDataSet.setValueTextColor(Color.MAGENTA);
        legRadarDataSet.setValueTextSize(15f);

        RadarData legRadarData = new RadarData();
        legRadarData.addDataSet(legRadarDataSet);

        String[] labels = {"Back Squat","Front Squat","Calf Raise","Leg Press","Leg Extension","Leg Curl",};
        XAxis xAxis = weightRadarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        weightRadarChart.getDescription().setText("Leg Lifts");
        weightRadarChart.setData(legRadarData);

    }


}