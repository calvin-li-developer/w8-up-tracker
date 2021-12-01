package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*

    This is the Edit Exercise Activity. It will allow the user to edit a custom exercise.
    In addition it will:
        - Give the user the option edit the name of the exercise (if not a preset)
        - Give the user the option edit the muscle group utilized (if not a preset)
        - Give the user the option to edit a picture for the exercise (if not a preset)
        - Give the user the option to edit/add/delete a certain set/rep/weight amount

 */

public class ExerciseEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_edit);
    }
}