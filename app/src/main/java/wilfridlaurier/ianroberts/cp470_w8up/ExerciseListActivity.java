package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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