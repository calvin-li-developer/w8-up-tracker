package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/*

    This is the View Exercise Activity. It will allow the user to see a certain exercise as well
    as it's set/reps/weight amounts.
    In addition it will:
        - Give the user the option to edit this exercise which will send them to
        the ExerciseEditActivity
        - Give the user the option to add a set/rep/weight to the exercise which will send them
        to the ExerciseEditActivity

 */

public class ExerciseViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);
    }
}