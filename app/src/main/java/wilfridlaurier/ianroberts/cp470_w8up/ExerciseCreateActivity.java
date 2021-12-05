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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_create);

        exerciseName = (EditText) findViewById(R.id.exerciseNameEdit);
        exerciseSets = (EditText) findViewById(R.id.exerciseSetsEdit);
        exerciseReps = (EditText) findViewById(R.id.exerciseRepsEdit);
        exerciseWeight = (EditText) findViewById(R.id.exerciseWeightEdit);


//        muscleGroupSpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.muscleGroups, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        muscleGroupSpinner.setAdapter(adapter);

        Spinner mySpinner = (Spinner) findViewById(R.id.exerciseMuscleGroupSpinner);
        mySpinner.setAdapter(new ArrayAdapter<MuscleGroup>(this, android.R.layout.simple_spinner_item, MuscleGroup.values()));

        exerciseCreateButton = findViewById(R.id.exerciseCreateButton);

        exerciseCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetRep newSetRep = new SetRep(Integer.parseInt(exerciseSets.getText().toString()),Integer.parseInt(exerciseReps.getText().toString()), (Integer.parseInt(exerciseWeight.getText().toString())));
                Exercise newExercise = new Exercise(exerciseName.getText().toString(),newSetRep,(MuscleGroup) muscleGroupSpinner.getSelectedItem());
                //TODO add this new exercise to the list of exercises for the user
                //TODO add a toast for the new exercise being created
                Intent goToExerciseOptionsIntent = new Intent(ExerciseCreateActivity.this, ExerciseListActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });
    }
}