package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

/*

    This is the Edit Exercise Activity. It will allow the user to edit a custom exercise.
    In addition it will:
        - Give the user the option edit the name of the exercise (if not a preset)
        - Give the user the option edit the muscle group utilized (if not a preset)
        - Give the user the option to edit a picture for the exercise (if not a preset)
        - Give the user the option to edit/add/delete a certain set/rep/weight amount

 */

public class ExerciseEditActivity extends AppCompatActivity {

    ArrayList<Exercise> userExercises;
    EditText exerciseName;
    Spinner muscleGroupSpinner;
    EditText exerciseSets;
    EditText exerciseReps;
    EditText exerciseWeight;
    Button exerciseCreateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_edit);

        exerciseName = (EditText) findViewById(R.id.exerciseNameEdit);
        exerciseSets = (EditText) findViewById(R.id.exerciseSetsEdit);
        exerciseReps = (EditText) findViewById(R.id.exerciseRepsEdit);
        exerciseWeight = (EditText) findViewById(R.id.exerciseWeightEdit);

        userExercises = new ArrayList<Exercise>();

        SetRep testSetRepOne = new SetRep(3,10,350);
        Exercise testExerciseOne = new Exercise("ExerciseTestOne",testSetRepOne,MuscleGroup.CHEST);

        SetRep testSetRepTwo = new SetRep(3,10,350);
        Exercise testExerciseTwo = new Exercise("ExerciseTestTwo",testSetRepTwo,MuscleGroup.BACK);

        userExercises.add(testExerciseOne);
        userExercises.add(testExerciseTwo);

//        muscleGroupSpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.muscleGroups, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        muscleGroupSpinner.setAdapter(adapter);

        Spinner muscleGroupSpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
        muscleGroupSpinner.setAdapter(new ArrayAdapter<MuscleGroup>(this, android.R.layout.simple_spinner_item, MuscleGroup.values()));

        //Spinner setRepSpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
        //setRepSpinner.setAdapter(new ArrayAdapter<MuscleGroup>(this, android.R.layout.simple_spinner_item,userExercises));

        exerciseCreateButton = findViewById(R.id.exerciseCreateButton);

        exerciseCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetRep newSetRep = new SetRep(Integer.parseInt(exerciseSets.getText().toString()),Integer.parseInt(exerciseReps.getText().toString()), (Integer.parseInt(exerciseWeight.getText().toString())));
                Exercise newExercise = new Exercise(exerciseName.getText().toString(),newSetRep,(MuscleGroup) muscleGroupSpinner.getSelectedItem());
                //TODO add this new exercise to the list of exercises for the user
                //TODO add a toast for the new exercise being created
                Intent goToExerciseOptionsIntent = new Intent(ExerciseEditActivity.this, ExerciseListActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });
    }
}