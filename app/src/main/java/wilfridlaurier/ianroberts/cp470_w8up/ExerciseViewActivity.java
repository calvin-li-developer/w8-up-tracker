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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*

    This is the View Exercise Activity. It will allow the user to see a certain exercise as well
    as it's set/reps/weight amounts.
    In addition it will:
        - Give the user the option to edit this exercise which will send them to
        the ExerciseEditActivity
        - Give the user the option to add a set/rep/weight to the exercise which will send them
        to the ExerciseEditActivity

 */

public class ExerciseViewActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ExerciseViewActivity";

    TextView exerciseName;
    TextView exerciseMuscleGroup;
    ListView setRepListView;
    EditText setConfigEdit;
    EditText repConfigEdit;
    Button setRepConfigAddButton;

    String exerciseID;
    String workoutID;
    String setRepID;

    SetRepsAdapter adapter;

    Exercise selectedExercise;

    ArrayList<Exercise> exerciseToView;
    ArrayList<SetRep> exerciseSetReps;

    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view);

        exerciseName = findViewById(R.id.exerciseNameText);
        exerciseMuscleGroup = findViewById(R.id.exerciseMuscleGroupText);
        setRepListView = findViewById(R.id.setRepsListView);
        setConfigEdit = findViewById(R.id.setConfigEdit);
        repConfigEdit = findViewById(R.id.repConfigEdit);
        setRepConfigAddButton = findViewById(R.id.setRepConfigAddButton);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("exerciseID")) {
                exerciseID = (String) intent.getExtras().get("exerciseID");
            }
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
            }
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference rootRef = database.getReference();
        DatabaseReference exerciseReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList");

        System.out.println("workoutID: "+exerciseReference.toString());

        // TODO siwtch the listview in this layout to be a listview of their progression for that exercise
        // TODO add below the list a button to add a new setrep object with an updated weight only

        exerciseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseToView = new ArrayList<>();
                for (DataSnapshot exercise : snapshot.getChildren()) {
                    Exercise temp = exercise.getValue(Exercise.class);
                    if(temp.getExerciseID().equals(exerciseID)) {
                        selectedExercise = exercise.getValue(Exercise.class);
                    }
                }
                exerciseName.setText(selectedExercise.getExerciseName());
                exerciseMuscleGroup.setText(selectedExercise.getMuscleGroupCategory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get exercise set reps", error.toException());
            }
        });

        DatabaseReference setRepsReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList").child(exerciseID).child("setRepConfigs");

        System.out.println("workoutID: "+setRepsReference.toString());

        setRepsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exerciseSetReps = new ArrayList<>();
                adapter = new SetRepsAdapter(getApplicationContext(), 0, exerciseSetReps);
                setRepListView.setAdapter(adapter);
                System.out.println("snapshot: "+snapshot.toString());
                for (DataSnapshot setRep : snapshot.getChildren()) {
                    SetRep temp = setRep.getValue(SetRep.class);
                    exerciseSetReps.add(temp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get exercise set reps", error.toException());
            }
        });

        setRepConfigAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetRep newSetRep = new SetRep(Integer.parseInt(setConfigEdit.getText().toString()),Integer.parseInt(repConfigEdit.getText().toString()));

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                fAuth = FirebaseAuth.getInstance();
                userID = fAuth.getCurrentUser().getUid();

                DatabaseReference rootRef = database.getReference();
                DatabaseReference workoutsReference = rootRef.child("users").child(userID);

                Map<String,Object> childUpdates;

                String setRepKey = workoutsReference.push().getKey();

                childUpdates = new HashMap<>();
                newSetRep.setSetRepID(setRepKey);
                childUpdates.put("/userExercises/" + exerciseID + "/setRepConfigs/" + setRepKey, newSetRep);
                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseID + "/setRepConfigs/" + setRepKey, newSetRep);

                workoutsReference.updateChildren(childUpdates);
                adapter.notifyDataSetChanged();
            }
        });

        setRepListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SetRep selectedSetRep = (SetRep) (setRepListView.getItemAtPosition(i));
                Intent showExercise = new Intent(getApplicationContext(),ExerciseViewSetRepActivity.class);
                showExercise.putExtra("setRepID",selectedSetRep.getSetRepID());
                showExercise.putExtra("exerciseID",exerciseID);
                showExercise.putExtra("workoutID",workoutID);
                startActivity(showExercise);
            }
        });

        setRepConfigAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetRep newSetRep = new SetRep(Integer.parseInt(setConfigEdit.getText().toString()),Integer.parseInt(repConfigEdit.getText().toString()));

                DatabaseReference setRepsReference = rootRef.child("users").child(userID);

                String setRepKey = setRepsReference.push().getKey();

                Map<String,Object> childUpdates;
                childUpdates = new HashMap<>();
                newSetRep.setSetRepID(setRepKey);
                childUpdates.put("/userExercises/" + exerciseID + "/setRepConfigs/" + setRepKey, newSetRep);
                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseID + "/setRepConfigs/" + setRepKey, newSetRep);

                setRepsReference.updateChildren(childUpdates);
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

    private class SetRepsAdapter extends ArrayAdapter<SetRep> {
        public SetRepsAdapter(Context ctx, int resource, ArrayList<SetRep> setRepsList) {
            super(ctx, resource,setRepsList);
        }

        @Override
        public View getView(int position, View result, ViewGroup parent) {
            SetRep setRep = getItem(position);

            if (result == null) {
                result = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_view_spinner_item, parent, false);
            }
            TextView setRepsTextView = result.findViewById(R.id.setRepsText);
            String temp = setRep.getSets() + "x" +setRep.getReps();
            setRepsTextView.setText(temp);

            return result;
        }
    }
}