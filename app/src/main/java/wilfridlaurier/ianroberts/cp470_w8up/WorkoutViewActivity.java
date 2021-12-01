package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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