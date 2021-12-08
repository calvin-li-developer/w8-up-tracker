package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

/*

    This is the View Exercise Activity. It will allow the user to see a certain exercise as well
    as it's set/reps/weight amounts.
    In addition it will:
        - Give the user the option to edit this exercise which will send them to
        the ExerciseEditActivity
        - Give the user the option to add a set/rep/weight to the exercise which will send them
        to the ExerciseEditActivity

 */

public class ExerciseViewActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ExerciseViewActivity";

    TextView exerciseName;
    TextView exerciseMuscleGroup;
    Spinner setRepSpinner;
    ListView setRepListView;

    String exerciseID;
    String workoutID;

    SetRepsAdapter adapter;

    Exercise selectedExercise;

    ArrayList<Exercise> exerciseToView;
    ArrayList<SetRep> exerciseSetReps;

    FirebaseAuth fAuth;
    String userID;

    String filterOption = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        exerciseName = findViewById(R.id.exerciseNameText);
        exerciseMuscleGroup = findViewById(R.id.exerciseMuscleGroupText);
        setRepSpinner = findViewById(R.id.exerciseSetRepsOptionsSpinner);
        setRepListView = findViewById(R.id.setRepsListView);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("exerciseID")) {
                exerciseID = (String) intent.getExtras().get("exerciseID");
            }
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference rootRef = database.getReference();
        DatabaseReference exerciseReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList");

        System.out.println("workoutID: "+exerciseReference.toString());

        // TODO siwtch the listview in this layout to be a listview of their progression for that exercise
        // TODO add below the list a button to add a new setrep object with an updated weight only

        exerciseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseToView = new ArrayList<>();
//                adapter = new SetRepsAdapter(getApplicationContext(), 0, exerciseSetReps);
//                setRepListView.setAdapter(adapter);
                System.out.println("snapshot: "+snapshot.toString());
                for (DataSnapshot exercise : snapshot.getChildren()) {
                    Exercise temp = exercise.getValue(Exercise.class);
                    System.out.println("exercise ID of temp: "+temp.getExerciseID());
                    System.out.println("exercise ID: "+exerciseID);
                    if(temp.getExerciseID().equals(exerciseID)) {
                        selectedExercise = exercise.getValue(Exercise.class);
                    }
                }
//                setRepSpinner.setAdapter(new SetRepsAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,exerciseSetReps));
//                adapter.notifyDataSetChanged();
                exerciseName.setText(selectedExercise.getExerciseName());
                exerciseMuscleGroup.setText(selectedExercise.getMuscleGroupCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get exercise set reps", error.toException());
            }
        });

        DatabaseReference setRepsReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList").child(exerciseID).child("setRepConfigs");

        System.out.println("workoutID: "+setRepsReference.toString());

        setRepsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseSetReps = new ArrayList<>();
                adapter = new SetRepsAdapter(getApplicationContext(), 0, exerciseSetReps,false);
                setRepListView.setAdapter(adapter);
                System.out.println("snapshot: "+snapshot.toString());
                for (DataSnapshot setRep : snapshot.getChildren()) {
                    SetRep temp = setRep.getValue(SetRep.class);
                    exerciseSetReps.add(temp);
                }
                setRepSpinner.setAdapter(new SetRepsAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,exerciseSetReps,true));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get exercise set reps", error.toException());
            }
        });

        setRepSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    private class SetRepsAdapter extends ArrayAdapter<SetRep> {
        Boolean isSpinner;
        public SetRepsAdapter(Context ctx, int resource, ArrayList<SetRep> setRepsList, Boolean spinner) {
            super(ctx, resource,setRepsList);
            this.isSpinner = spinner;
        }

        @Override
        public View getView(int position, View result, ViewGroup parent) {
            SetRep setRep = getItem(position);
            WeightProgress weightProgress =  setRep.getWeightProgress();
            int weight = weightProgress.getWeight();
            Date date = weightProgress.getProgressDate();
            SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");

            if(isSpinner){
                if (result == null) {
                    result = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_view_spinner_item, parent, false);
                }
                TextView setRepsTextView = (TextView) result.findViewById(R.id.setRepsText);
                setRepsTextView.setText(setRep.getSets() + "x" +setRep.getReps());
            }
            else {
                if (result == null) {
                    result = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_view_list_item, parent, false);
                }
                TextView weightAmountTextView = (TextView) result.findViewById(R.id.setCustomWeightAmount);
                TextView dateTextView = (TextView) result.findViewById(R.id.setCustomDate);
                weightAmountTextView.setText(Integer.toString(weight));
                dateTextView.setText(formattedDate.format(date));
            }
            return result;
        }
    }

//    private class SpinnerAdapter extends ArrayAdapter<SetRep> {
//        public SpinnerAdapter(Context ctx, int resource, ArrayList<SetRep> setRepsList) {
//            super(ctx, resource,setRepsList);
//        }
//
//        @Override
//        public View getView(int position, View result, ViewGroup parent) {
//            SetRep setRep = getItem(position);
//            WeightProgress weightProgress =  setRep.getWeightProgress();
//            int weight = weightProgress.getWeight();
//            Date date = weightProgress.getProgressDate();
//            SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
//
//            if(result == null){
//                result = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_view_spinner_item,parent,false);
//            }
//
//            TextView setRepsTextView = (TextView) result.findViewById(R.id.setRepsText);
//            setRepsTextView.setText(setRep.getSets() + "x" +setRep.getReps());
//
//            return result;
//        }
//    }


}