package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CreateNewRoutineActivity extends AppCompatActivity {

    protected final String ACTIVITY_NAME = "CreateNewRoutineAct";

    Button exerciseOptionsButton;
    Button viewWorkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_routine);
        exerciseOptionsButton = findViewById(R.id.exerciseOptionsButton);
        viewWorkoutButton = findViewById(R.id.viewWorkoutButton);

        Log.i(ACTIVITY_NAME, "Gets to here");

        exerciseOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseOptionsIntent = new Intent(CreateNewRoutineActivity.this, ExerciseOptionsActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });

        viewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToWorkoutIntent = new Intent(CreateNewRoutineActivity.this, RoutineActivity.class);
                startActivity(goToWorkoutIntent);
            }
        });
    }
}