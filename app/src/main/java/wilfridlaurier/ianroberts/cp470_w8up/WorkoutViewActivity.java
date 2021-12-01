package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*

    This is the View Workout Activity. It will allow the user to see a certain workout
    In addition it will:
        - Give the user the option to edit this workout which will send them to
        the Workout
        - Give the user the option to add a set/rep/weight to the exercise which will send them
        to the ExerciseEditActivity

 */

public class WorkoutViewActivity extends AppCompatActivity {

    Button viewExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);

        viewExerciseButton = findViewById(R.id.viewExerciseButton);

        viewExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExercise = new Intent(WorkoutViewActivity.this, ExerciseViewActivity.class);
                startActivity(goToExercise);
            }
        });
    }
}