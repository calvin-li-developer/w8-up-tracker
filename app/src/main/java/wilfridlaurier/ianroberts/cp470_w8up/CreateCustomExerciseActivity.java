package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateCustomExerciseActivity extends AppCompatActivity {

    Button exerciseOptionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_exercise);
        exerciseOptionsButton = findViewById(R.id.exerciseOptionsButton2);

        exerciseOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseOptionsIntent = new Intent(CreateCustomExerciseActivity.this, ExerciseOptionsActivity.class);
                startActivity(goToExerciseOptionsIntent);
            }
        });
    }
}