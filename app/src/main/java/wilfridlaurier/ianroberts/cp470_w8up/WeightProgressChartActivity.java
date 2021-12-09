package wilfridlaurier.ianroberts.cp470_w8up;


import static android.app.ProgressDialog.show;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class WeightProgressChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String ACTIVITY_NAME = "WeightProgressChartActivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_progress_chart);

        /*  This activity will first use a list of muscleGroupCategories for the user to choose
            It will then pull all setRep Objects based on muscle group
            Finally it will input into charts by date and weight
         */

        // Instantiations
        String userID;
        String exerciseID = null;
        String[] muscleGroupArrayList = {"Arms", "Chest", "Back", "Legs", "Abs"};
        ArrayList<Exercise> selectedExercises = new ArrayList<>();
        HashMap<String, HashMap<String, SetRep>> eSetRepMap = new HashMap<>();
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        // Dropdown line chart spinner set up
        Spinner lineSpinner = findViewById((R.id.lineSpinner));

        // Dropdown progress spinner set up
        Spinner progressSpinner = findViewById(R.id.progressSpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, muscleGroupArrayList);
        progressSpinner.setAdapter(spinnerAdapter);
        progressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override // Setting selectedMuscleGroup to current selection on spinner
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String temp = (String) progressSpinner.getSelectedItem();
                Toast.makeText(parent.getContext(), temp, Toast.LENGTH_SHORT).show();
                spinnerAdapter.notifyDataSetChanged();

                // Pulling user exercise list into selected exercises
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference rootRef = db.getReference();
                DatabaseReference exerciseReference = rootRef.child("users").child(userID).child("userExercises");
                exerciseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        selectedExercises.clear();
                        for (DataSnapshot exercise : snapshot.getChildren()) {
                            Exercise temp = exercise.getValue(Exercise.class);
//                            System.out.println(progressSpinner.getSelectedItem());
//                            System.out.println(temp.getMuscleGroupCategory());

                            String temp1 = progressSpinner.getSelectedItem().toString().trim();
                            String temp2 = temp.getMuscleGroupCategory().trim();
                            if(temp1.equals(temp2)) {
                                selectedExercises.add(temp);
                                System.out.println("equals");
                                System.out.println(selectedExercises.size());
                                for (Exercise e : selectedExercises) {
                                    System.out.println("Selected Exercises: " + e.getExerciseName().trim());
                                    eSetRepMap.put(e.getExerciseName().trim(), e.getSetRepConfigs());
                                }
                            }
                        }


                        setUpData(selectedExercises, eSetRepMap);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(ACTIVITY_NAME, "Could not get exercise set reps", error.toException());
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        // Line Chart Set up
        LineChart lChart = findViewById(R.id.weightLineChart);
        lChart.setDragEnabled(true);
        ArrayList<Entry> lineYvals = new ArrayList<>();
        LineDataSet lineLegSet1 = new LineDataSet(lineYvals, "Back Squats");
        lineLegSet1.setFillAlpha(110);
        LineData lineLegData1 = new LineData(lineLegSet1);
        lChart.setData(lineLegData1);

        // Radar Chart Set Up
        RadarChart weightRadarChart = findViewById(R.id.weightRadarChart);
        ArrayList<RadarEntry> legWeights = new ArrayList<>();
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

    public void setUpData(ArrayList<Exercise> eList, HashMap<String, HashMap<String, SetRep>> chartExerciseMap) {
        System.out.println("-------------------------------------");
        System.out.println(eList.size());
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("y M d");

        // Organizing HashMap chartExerciseMap
        HashMap<String, int[][]> selExerciseNames = new HashMap<>();
        HashMap<int[][], Integer> weightBySets = new HashMap<int[][], Integer>();
//        HashMap<int[][], Date>;

        //TODO Load the dates and weights of the chosenExercise in secondSpinner into Entries

        for (Map.Entry element : chartExerciseMap.entrySet()) {
            String key = (String) element.getKey();

            HashMap<String, SetRep> currSetRep = chartExerciseMap.get(key);
            for (Map.Entry innerElement : currSetRep.entrySet()) {
                String innerKey = (String) innerElement.getKey();


            }
        }

        // Line Chart Set up
        LineChart legChart = findViewById(R.id.weightLineChart);


        ArrayList<Entry> lineYvals = new ArrayList<>();

        lineYvals.add(new Entry(0,195f));
        lineYvals.add(new Entry(1,135f));
        lineYvals.add(new Entry(2,115f));
        lineYvals.add(new Entry(3,165f));
        lineYvals.add(new Entry(4,190f));
        lineYvals.add(new Entry(5,225f));

        LineDataSet lineLegSet1 = new LineDataSet(lineYvals, "Back Squats");
        lineLegSet1.setFillAlpha(110);

        LineData lineLegData1 = new LineData(lineLegSet1);
        legChart.setData(lineLegData1);


        // Radar Chart Set Up
        RadarChart weightRadarChart = findViewById(R.id.weightRadarChart);

        ArrayList<RadarEntry> legWeights = new ArrayList<>();
        legWeights.add(new RadarEntry(205*2)); //Back squat
        legWeights.add(new RadarEntry(155*2)); //Front squat
        legWeights.add(new RadarEntry(165*2)); //Calf raise
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

