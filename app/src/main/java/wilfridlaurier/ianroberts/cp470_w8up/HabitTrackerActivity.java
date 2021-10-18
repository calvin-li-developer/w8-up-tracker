package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;

public class HabitTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        ImageButton hydrationButton = findViewById(R.id.hydration);
        hydrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitTrackerActivity.this, HydrationActivity.class);
                startActivity(intent);
            }
        });
    }
}