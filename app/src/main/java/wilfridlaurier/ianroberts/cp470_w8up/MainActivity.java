package wilfridlaurier.ianroberts.cp470_w8up;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import wilfridlaurier.ianroberts.cp470_w8up.databinding.ActivityToolbarBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityToolbarBinding binding;

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

        binding = ActivityToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu m) {
        super.onCreateOptionsMenu(m);
        getMenuInflater().inflate(R.menu.toolbar_menu, m);
        return true;
    }

    public void showAlert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Info");
        alert.setMessage("To use this interface, simply press one of the 4 buttons that matches your desired activity. \n Developers: Mohamed Assan, Keadon Harrison, Chang Li, Scoban Pham, Ian Roberts \n version 1.0");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alert.create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem mi) {
        int id = mi.getItemId();
        switch (id) {
            case R.id.workouts:
                openWorkouts();
                break;
            case R.id.weightTracking:
                openWeightTracker();
                break;
            case R.id.habitTracking:
                openHabitTracker();
                break;
            case R.id.profile:
                openSocialMedia();
                break;
            case R.id.Help:
                showAlert();

        }
        return false;
    }

    private ImageButton workout;
    private ImageButton weightTracker;
    private ImageButton socialMedia;
    private ImageButton habitTracker;

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

}