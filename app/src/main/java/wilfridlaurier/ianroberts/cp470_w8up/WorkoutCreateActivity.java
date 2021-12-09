package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*

    This is the Create Workout Activity. It will allow the user create a workout and add
    exercises to it.
    In addition it will:
        - Allow the user to name the workout
        - Allow the user click on the add exercise button which will take them to
        the ExerciseListActivity
        - Allow the user to set the muscle group of the workout

 */

public class WorkoutCreateActivity extends AppCompatActivity {

    protected final String ACTIVITY_NAME = "CreateNewRoutineAct";

    EditText workoutName;
    Spinner muscleGroupSpinner;
    Switch isPinnedSwitch;
    Button workoutCreateButton;
//    Button workoutAddExerciseButton;

    FirebaseAuth fAuth;
    String userID;
//    String workoutID;
//    String exerciseID;
//    Boolean addedExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        // TODO figure out if this makes sense when doing the list of exercises to add to the workout
//        Intent intent = getIntent();
//        if (intent != null) {
//            if (intent.hasExtra("exerciseChosen")) {
//                exerciseID = (String) intent.getExtras().get("exerciseCreated");
//            }
//            if (intent.hasExtra("addedExercise")) {
//                addedExercise = (Boolean) intent.getExtras().get("addedExercise");
//            }
//            if (intent.hasExtra("workoutID")) {
//                workoutID = (String) intent.getExtras().get("workoutID");
//            }
//        }

        workoutName = findViewById(R.id.workoutNameEdit);
        muscleGroupSpinner = findViewById(R.id.workoutMuscleGroupSpinner);
        isPinnedSwitch = findViewById(R.id.isPinnedSwitch);
        workoutCreateButton = findViewById(R.id.workoutCreateButton);
//        workoutAddExerciseButton = findViewById(R.id.workoutAddExerciseButton);

        Spinner muscleGroupSpinner = (Spinner) findViewById(R.id.workoutMuscleGroupSpinner);
        muscleGroupSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.muscleGroups)));

        // TODO add functionaility to add exercises using a button that sends them to create exercise activity

//        workoutAddExerciseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                fAuth = FirebaseAuth.getInstance();
//                userID = fAuth.getCurrentUser().getUid();
//
//                Intent goToExerciseListIntent = new Intent(WorkoutCreateActivity.this, ExerciseListActivity.class);
//                // TODO when you send them to the exercise list when they choose an exercise add it directly to the workoutID that you have created before the workout wsa actually created
////                goToExerciseListIntent.putExtra("createdWorkoutID", newWorkout.getWorkoutID());
////                startActivity(goToWorkoutViewIntent);
//            }
//        });

        workoutCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO update the muscleGroup array list to be referenced properly in the database with key id
                ArrayList<String> muscleGroups = new ArrayList<>();
                muscleGroups.add(muscleGroupSpinner.getSelectedItem().toString());
                Workout newWorkout = new Workout(workoutName.getText().toString(),muscleGroups,isPinnedSwitch.isChecked());
                //TODO add the exerciseArray list that you are gonna create in this activity

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                fAuth = FirebaseAuth.getInstance();
                userID = fAuth.getCurrentUser().getUid();

                DatabaseReference rootRef = database.getReference();
                DatabaseReference workoutsReference = rootRef.child("users").child(userID).child("userWorkouts");

                String key = workoutsReference.push().getKey();

                Map<String,Object> childUpdates = new HashMap<>();
                newWorkout.setWorkoutID(key);
                childUpdates.put("/" + key, newWorkout);

                workoutsReference.updateChildren(childUpdates);

                finish();
            }
        });
    }
}