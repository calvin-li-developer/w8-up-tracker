package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RoutineActivity extends AppCompatActivity {

    Button viewExerciseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        viewExerciseButton = findViewById(R.id.viewExerciseButton);

        viewExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExercise = new Intent(RoutineActivity.this, ViewExerciseActivity.class);
                startActivity(goToExercise);
            }
        });
    }
}