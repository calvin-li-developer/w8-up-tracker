package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageButton;

import java.util.Date;

public class HabitTrackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_tracker);
        ImageButton hydrationButton = findViewById(R.id.hydration);
        ImageButton reminderButton = findViewById(R.id.reminder);
        CalendarView calendar = findViewById(R.id.calendar);

        hydrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitTrackerActivity.this, HydrationActivity.class);
                startActivity(intent);
            }
        }
        );

        hydrationButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(HabitTrackerActivity.this, HydrationActivity.class);
               startActivity(intent);
           }
       }
       );

        reminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HabitTrackerActivity.this, ReminderActivity.class);
                startActivity(intent);
            }
        });


    }
}