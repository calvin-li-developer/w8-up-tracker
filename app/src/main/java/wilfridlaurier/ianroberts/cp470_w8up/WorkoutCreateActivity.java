package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class WorkoutCreateActivity extends AppCompatActivity {

    protected final String ACTIVITY_NAME = "CreateNewRoutineAct";

    Button exerciseOptionsButton;
    Button viewWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);
        exerciseOptionsButton = findViewById(R.id.exerciseOptionsButton);
        viewWorkoutButton = findViewById(R.id.viewWorkoutButton);

        Log.i(ACTIVITY_NAME, "Gets to here");

        exerciseOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseOptionsIntent = new Intent(WorkoutCreateActivity.this, ExerciseListActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });

        viewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToWorkoutIntent = new Intent(WorkoutCreateActivity.this, WorkoutViewActivity.class);
                startActivity(goToWorkoutIntent);
            }
        });
    }
}