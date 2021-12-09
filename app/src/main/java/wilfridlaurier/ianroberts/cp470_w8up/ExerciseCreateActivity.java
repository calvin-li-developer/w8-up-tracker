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
import java.util.Date;
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
    Boolean createWorkoutCallBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_create);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
            }
//            if(intent.hasExtra("createWorkoutCallBack")){
//                createWorkoutCallBack = (Boolean) intent.getExtras().get("createWorkoutCallBack");
//            }
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
// BELOW IS USED TO INITIALIZE SOME TEST EXERCISES AND SETREPS AND PUT THEM IN THE DATABASE
//        SetRep testSetRepOne = new SetRep(3,10,350);
//        Exercise testExerciseOne = new Exercise("ExerciseTestOne","Chest");
//
//        SetRep testSetRepTwo = new SetRep(3,10,350);
//        Exercise testExerciseTwo = new Exercise("ExerciseTestTwo","Back");
//
//        String exerciseKey = exercisesReference.push().getKey();
//
//        Map<String,Object> childUpdates = new HashMap<>();
//        testExerciseOne.setExerciseID(exerciseKey);
//        childUpdates.put("/userExercises/" + exerciseKey, testExerciseOne);
//        childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey, testExerciseOne);
//
//        exercisesReference.updateChildren(childUpdates);
//
//        String setRepKey = exercisesReference.push().getKey();
//
//        childUpdates = new HashMap<>();
//        testSetRepOne.setSetRepID(setRepKey);
//        childUpdates.put("/userExercises/" + exerciseKey + "/setRepConfigs/" + setRepKey, testSetRepOne);
//        childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey + "/setRepConfigs/" + setRepKey, testSetRepOne);
//
//        exercisesReference.updateChildren(childUpdates);
//
//        exerciseKey = exercisesReference.push().getKey();
//
//        childUpdates = new HashMap<>();
//        testExerciseTwo.setExerciseID(exerciseKey);
//        System.out.println(testExerciseTwo.getExerciseName() + " " + testExerciseTwo.getMuscleGroupCategory());
//        childUpdates.put("/userExercises/" + exerciseKey, testExerciseTwo);
//        childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey, testExerciseTwo);
//
//        exercisesReference.updateChildren(childUpdates);
//
//        setRepKey = exercisesReference.push().getKey();
//
//        childUpdates = new HashMap<>();
//        testSetRepTwo.setSetRepID(setRepKey);
//        childUpdates.put("/userExercises/" + exerciseKey + "/setRepConfigs/" + setRepKey, testSetRepTwo);
//        childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey + "/setRepConfigs/" + setRepKey, testSetRepTwo);
//
//        exercisesReference.updateChildren(childUpdates);


        // TODO delete once you check to see if you need it
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
                WeightProgress newWeightProgress = new WeightProgress((Integer.parseInt(exerciseWeight.getText().toString())), new Date());
                SetRep newSetRep = new SetRep(Integer.parseInt(exerciseSets.getText().toString()),Integer.parseInt(exerciseReps.getText().toString()));
                String eName = exerciseName.getText().toString();
                String eMuscle = muscleGroupSpinner.getSelectedItem().toString();
//                System.out.println("New Exercise Name :"+eName+"New Exercise Muscle: "+eMuscle);
                Exercise newExercise = new Exercise(eName,eMuscle);
                //TODO add a toast for the new exercise being created

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                fAuth = FirebaseAuth.getInstance();
                userID = fAuth.getCurrentUser().getUid();

                DatabaseReference rootRef = database.getReference();
                DatabaseReference workoutsReference = rootRef.child("users").child(userID);

                String exerciseKey = workoutsReference.push().getKey();

                Map<String,Object> childUpdates = new HashMap<>();
                newExercise.setExerciseID(exerciseKey);
                childUpdates.put("/userExercises/" + exerciseKey, newExercise);
                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey, newExercise);

                workoutsReference.updateChildren(childUpdates);

                String setRepKey = workoutsReference.push().getKey();

                childUpdates = new HashMap<>();
                newSetRep.setSetRepID(setRepKey);
                childUpdates.put("/userExercises/" + exerciseKey + "/setRepConfigs/" + setRepKey, newSetRep);
                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey + "/setRepConfigs/" + setRepKey, newSetRep);

                workoutsReference.updateChildren(childUpdates);

                String weightProgressKey = workoutsReference.push().getKey();

                childUpdates = new HashMap<>();
                newWeightProgress.setWeightProgressID(weightProgressKey);
                childUpdates.put("/userExercises/" + exerciseKey + "/setRepConfigs/" + setRepKey + "/weightProgressTracking/" + weightProgressKey, newWeightProgress);
                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseKey + "/setRepConfigs/" + setRepKey + "/weightProgressTracking/" + weightProgressKey, newWeightProgress);

                workoutsReference.updateChildren(childUpdates);

                Intent goBackToWorkoutViewIntent = new Intent(ExerciseCreateActivity.this, WorkoutViewActivity.class);
                goBackToWorkoutViewIntent.putExtra("workoutID",workoutID);
                goBackToWorkoutViewIntent.putExtra("exerciseCreated",exerciseKey);
                startActivity(goBackToWorkoutViewIntent);

            }
        });
    }
}