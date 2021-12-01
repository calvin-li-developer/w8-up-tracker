package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*

    This is the Create Exercise Activity. It will allow the user to create a custom exercise.
    In addition it will:
        - Give the user the option choose the name of the exercise
        - Give the user the option choose the muscle group utilized
        - Give the user the option to add a picture for the exercise
        - Give the user the option to start of with a certain set/rep/weight amount

 */

public class ExerciseCreateActivity extends AppCompatActivity {

    Button exerciseOptionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_create);
        exerciseOptionsButton = findViewById(R.id.exerciseOptionsButton2);

        exerciseOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseOptionsIntent = new Intent(ExerciseCreateActivity.this, ExerciseListActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });
    }
}