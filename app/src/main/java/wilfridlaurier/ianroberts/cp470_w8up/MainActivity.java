package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ImageButton workout;
    private ImageButton weightTracker;
    private ImageButton socialMedia;
    private ImageButton habitTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        workout = (ImageButton) findViewById(R.id.workoutsButton);
        weightTracker = (ImageButton) findViewById(R.id.weightTrackerButton);
        socialMedia = (ImageButton) findViewById(R.id.socialMediaButton);
        habitTracker = (ImageButton) findViewById(R.id.habitTrackerButton);

        workout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openWorkouts();
            }
        });
        weightTracker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openWeightTracker();
            }
        });
        socialMedia.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openSocialMedia();
            }
        });
        habitTracker.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                openHabitTracker();
            }
        });

    }
    public void openWorkouts(){
        Intent wo = new Intent(this, AllWorkoutsActivity.class);
        startActivity(wo);
    }
    public void openWeightTracker(){
        Intent wt = new Intent(this, WeightProgressChartActivity.class);
        startActivity(wt);
    }
    public void openSocialMedia(){
        Intent sm = new Intent(this, SocialMediaActivity.class);
        startActivity(sm);
    }
    public void openHabitTracker(){
        Intent ht = new Intent(this, HabitTrackerActivity.class);
        startActivity(ht);
    }

    public void logout(View v)
    {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        finish();
    }
}