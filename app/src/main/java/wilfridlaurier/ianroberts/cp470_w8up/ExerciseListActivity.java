package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*

    This is the Exercise List Activity. It will allow the user to see a list of all the exercises
    so that they can add them to their workout.
    In addition it will:
        - Give the user the option to search through the list of workouts to add one
        - Give the user the option to filter by muscle group / custom workouts
        - Give the user the option to create their own custom exercise which will send them
        to the ExerciseCreateActivity

 */

public class ExerciseListActivity extends AppCompatActivity {

    Button createCustomExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        createCustomExerciseButton = findViewById(R.id.createCustomExerciseButton);

        createCustomExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateCustomExercise = new Intent(ExerciseListActivity.this, ExerciseCreateActivity.class);
                startActivity(goToCreateCustomExercise);
            }
        });
    }
}