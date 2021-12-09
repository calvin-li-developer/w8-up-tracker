package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/*

    This is the View Workout Activity. It will allow the user to see a certain workout
    In addition it will:
        - Give the user the option to edit this workout which will send them to
        the Workout
        - Give the user the option to add a set/rep/weight to the exercise which will send them
        to the ExerciseEditActivity

 */

public class WorkoutViewActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "WorkoutViewActivity";

    SearchView exerciseSearchView;
    ImageButton newExerciseButton;
    ImageButton filterExercisesButton;
    ListView exercisesListView;

    FirebaseAuth fAuth;
    String userID;
    String workoutID;
    String createdWorkoutID;
    String exerciseID;

    ArrayList<Exercise> userExercises;

    ExercisesAdapter adapter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        exerciseSearchView = findViewById(R.id.searchExercisesSearchView);
        newExerciseButton = findViewById(R.id.newExerciseButton);
        filterExercisesButton = findViewById(R.id.filterExercisesButton);
        exercisesListView = findViewById(R.id.exercisesListView);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
                System.out.println("workoutID: "+workoutID);
            }
            if (intent.hasExtra("createdWorkoutID")) {
                createdWorkoutID = (String) intent.getExtras().get("createdWorkoutID");
                workoutID = createdWorkoutID;
            }
        }


        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        DatabaseReference rootRef = database.getReference();

        DatabaseReference exercisesReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList");

        System.out.println("workoutID: "+exercisesReference.toString());

        exercisesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userExercises = new ArrayList<>();
                adapter = new ExercisesAdapter(getApplicationContext(), 0, userExercises);
                exercisesListView.setAdapter(adapter);
                System.out.println("snapshot: "+snapshot.toString());
                for (DataSnapshot exercise : snapshot.getChildren()) {
                    Exercise temp = exercise.getValue(Exercise.class);
                    userExercises.add(temp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get user workout", error.toException());
            }
        });

        exerciseSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Exercise> filterExercises = new ArrayList<>();
                for (Exercise exercise : userExercises) {
                    if (exercise.getExerciseName().toLowerCase().contains(newText.toLowerCase())) {
//                        if (filterOption.equals("ALL")) {
                            filterExercises.add(exercise);
//                        } else {
//                            for (String muscleGroup : exercise.getMuscleGroupCategories()) {
//                                if (muscleGroup.toLowerCase().equals(filterOption.toLowerCase())) {
//                                    filterExercises.add(exercise);
//                                    break;
//                                }
//                            }
//                        }
                    }
                }
                adapter = new ExercisesAdapter(getApplicationContext(), 0, filterExercises);
                exercisesListView.setAdapter(adapter);
                return false;
            }
        });

        newExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked add new exercise");
                Intent newExerciseIntent = new Intent(getApplicationContext(), ExerciseCreateActivity.class);
                newExerciseIntent.putExtra("workoutID",workoutID);
                startActivity(newExerciseIntent);
                //TODO add logic at create exercise to check if it is adding exercise to a specific workout or just the list as a whole
            }
        });

        exercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise selectedExercise = (Exercise) (exercisesListView.getItemAtPosition(i));
                Intent showExercise = new Intent(getApplicationContext(),ExerciseViewActivity.class);
                showExercise.putExtra("exerciseID",selectedExercise.getExerciseID());
                showExercise.putExtra("workoutID",workoutID);
                startActivity(showExercise);
            }
        });

        DatabaseReference thisWorkoutRef = database.getReference("users/" + userID + "/userWorkouts/" + workoutID);

        thisWorkoutRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again

                Workout temp =  dataSnapshot.getValue(Workout.class);
                if (temp != null) {
                    Log.d(ACTIVITY_NAME, "Datasnapshot W:" + temp.getWorkoutName());
                    myToolbar.setTitle(temp.getWorkoutName());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(ACTIVITY_NAME, "Failed to read value.", error.toException());
            }
        });
        // Should be called last at all times
        setSupportActionBar(myToolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    private class ExercisesAdapter extends ArrayAdapter<Exercise> {
        public ExercisesAdapter(Context ctx, int resource, ArrayList<Exercise> exercisesList) {
            super(ctx, resource, exercisesList);
        }

        @Override
        public View getView(int position, View result, ViewGroup parent) {
            Exercise exercise = getItem(position);

            if (result == null) {
                result = LayoutInflater.from(getContext()).inflate(R.layout.activity_workout_view_list_item, parent, false);
            }

            TextView exerciseName = (TextView) result.findViewById(R.id.exerciseName);
            exerciseName.setText(exercise.getExerciseName());

            return result;
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu_workout, m);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem mi)
    {
        switch(mi.getItemId()) {
            case R.id.menu_one:
                Log.d("Toolbar","menu_one Selected");
                DatabaseReference thisWorkoutRef = database.getReference("users/" + userID + "/userWorkouts/" + workoutID);
                thisWorkoutRef.removeValue();
                finish();
                return true;
            default:
                Log.d("Toolbar","No Menu Selected");
                return true;
        }
    }
}