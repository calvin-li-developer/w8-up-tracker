package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExerciseOptionsActivity extends AppCompatActivity {

    Button createCustomExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_options);
        createCustomExerciseButton = findViewById(R.id.createCustomExerciseButton);

        createCustomExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateCustomExercise = new Intent(ExerciseOptionsActivity.this, CreateCustomExerciseActivity.class);
                startActivity(goToCreateCustomExercise);
            }
        });
    }
}