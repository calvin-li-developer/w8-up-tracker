package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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

    Workout userWorkout = new Workout();
    ArrayList<Exercise> userExercises;

    ExercisesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_view);

        exerciseSearchView = findViewById(R.id.searchExercisesSearchView);
        newExerciseButton = findViewById(R.id.newExerciseButton);
        filterExercisesButton = findViewById(R.id.filterExercisesButton);
        exercisesListView = findViewById(R.id.exercisesListView);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
            }
            if (intent.hasExtra("createdWorkoutID")) {
                createdWorkoutID = (String) intent.getExtras().get("createdWorkoutID");
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference rootRef = database.getReference();
        DatabaseReference workoutReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID);

        workoutReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //DataSnapshot workout = snapshot.getChildren();
                userWorkout = snapshot.getValue(Workout.class);
                System.out.println(userWorkout.getWorkoutID());
                System.out.println(userWorkout.getWorkoutName());
                System.out.println(userWorkout.getNumberOfExercises());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get user workout", error.toException());
            }
        });

        userExercises = userWorkout.getExerciseList();
        for(Exercise exercise : userExercises) {
            Log.i(ACTIVITY_NAME, exercise.getExerciseName());
        }
        adapter = new ExercisesAdapter(getApplicationContext(), 0, userExercises);
        exercisesListView.setAdapter(adapter);


//        DatabaseReference exercisesReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList");
//
//        exercisesReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                userExercises = new ArrayList<>();
//                adapter = new ExercisesAdapter(getApplicationContext(), 0, userWorkout.getExerciseList());
//                exercisesListView.setAdapter(adapter);
//                for (DataSnapshot exercise : snapshot.getChildren()) {
//                    userExercises.add(exercise.getValue(Exercise.class));
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(ACTIVITY_NAME, "Could not get user workouts", error.toException());
//            }
//        });

//        for(Exercise exercise : userExercises) {
//            Log.i(ACTIVITY_NAME, exercise.getExerciseName());
//        }
//        adapter = new ExercisesAdapter(getApplicationContext(), 0, userExercises);
//        exercisesListView.setAdapter(adapter);


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

        adapter = new ExercisesAdapter(getApplicationContext(),0,userExercises);
        exercisesListView.setAdapter(adapter);

        exercisesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Exercise selectedExercise = (Exercise) (exercisesListView.getItemAtPosition(i));
                Intent showExercise = new Intent(getApplicationContext(),ExerciseViewActivity.class);
                //TODO switch this to getExerciseID()
                showExercise.putExtra("exerciseID",selectedExercise.getExerciseName());
                startActivity(showExercise);
            }
        });
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
}