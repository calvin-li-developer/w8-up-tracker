package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*

    This is the Create Exercise Activity. It will allow the user to create a custom exercise.
    In addition it will:
        - Give the user the option choose the name of the exercise
        - Give the user the option choose the muscle group utilized
        - Give the user the option to start off with a certain set/rep/weight amount

 */

public class ExerciseCreateActivity extends AppCompatActivity {

    EditText exerciseName;
    Spinner muscleGroupSpinner;
    EditText exerciseSets;
    EditText exerciseReps;
    EditText exerciseWeight;
    Button exerciseCreateButton;

    FirebaseAuth fAuth;
    String userID;
    String workoutID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_create);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
            }
        }

        exerciseName = (EditText) findViewById(R.id.exerciseNameEdit);
        exerciseSets = (EditText) findViewById(R.id.exerciseSetsEdit);
        exerciseReps = (EditText) findViewById(R.id.exerciseRepsEdit);
        exerciseWeight = (EditText) findViewById(R.id.exerciseWeightEdit);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference rootRef = database.getReference();
        DatabaseReference exercisesReference = rootRef.child("users").child(userID);

        SetRep testSetRepOne = new SetRep(3,10,350);
        Exercise testExerciseOne = new Exercise("ExerciseTestOne",testSetRepOne,"Chest");

        SetRep testSetRepTwo = new SetRep(3,10,350);
        Exercise testExerciseTwo = new Exercise("ExerciseTestTwo",testSetRepTwo,"Back");

        ArrayList<Exercise> userExercises = new ArrayList<>();
        userExercises.add(testExerciseOne);
        userExercises.add(testExerciseTwo);

        for(Exercise exercise: userExercises) {
            String key = exercisesReference.push().getKey();

            Map<String,Object> childUpdates = new HashMap<>();
            exercise.setExerciseID(key);
            childUpdates.put("/userExercises/" + key, exercise);
            childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + key, exercise);

            exercisesReference.updateChildren(childUpdates);
        }

        //exercisesReference.setValue(userExercises);


//        muscleGroupSpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.muscleGroups, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        muscleGroupSpinner.setAdapter(adapter);

        Spinner muscleGroupSpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
        muscleGroupSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.muscleGroups)));

        exerciseCreateButton = findViewById(R.id.exerciseCreateButton);

        exerciseCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetRep newSetRep = new SetRep(Integer.parseInt(exerciseSets.getText().toString()),Integer.parseInt(exerciseReps.getText().toString()), (Integer.parseInt(exerciseWeight.getText().toString())));
                Exercise newExercise = new Exercise(exerciseName.getText().toString(),newSetRep,(String) muscleGroupSpinner.getSelectedItem());
                //TODO add this new exercise to the list of exercises for the user
                //TODO add a toast for the new exercise being created
                Intent goToExerciseOptionsIntent = new Intent(ExerciseCreateActivity.this, ExerciseListActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });
    }
}