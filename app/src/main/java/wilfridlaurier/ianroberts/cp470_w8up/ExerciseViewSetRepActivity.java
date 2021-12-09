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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

public class ExerciseViewSetRepActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ExerciseViewSetRep";

    TextView setText;
    TextView repText;
    ListView weightProgressListView;
    EditText weightProgressEdit;
    Button setRepAddWeightProgressButton;

    WeightProgressAdapter adapter;

    ArrayList<WeightProgress> weightProgresses;

    FirebaseAuth fAuth;
    String userID;

    String exerciseID;
    String workoutID;
    String setRepID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view_set_rep);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("setRepID")) {
                setRepID = (String) intent.getExtras().get("setRepID");
            }
            if (intent.hasExtra("exerciseID")) {
                exerciseID = (String) intent.getExtras().get("exerciseID");
            }
            if (intent.hasExtra("workoutID")) {
                workoutID = (String) intent.getExtras().get("workoutID");
            }
        }

        setText = findViewById(R.id.setText);
        repText = findViewById(R.id.repText);
        weightProgressListView = findViewById(R.id.weightProgressListView);
        weightProgressEdit = findViewById(R.id.weightProgressEdit);
        setRepAddWeightProgressButton = findViewById(R.id.setRepAddWeightProgressButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference rootRef = database.getReference();
        DatabaseReference setRepsReference = rootRef.child("users").child(userID).child("userWorkouts").child(workoutID).child("exerciseList").child(exerciseID).child("setRepConfigs").child(setRepID).child("WeightProgressTracking");

        setRepsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                weightProgresses = new ArrayList<>();
                adapter = new WeightProgressAdapter(getApplicationContext(), 0, weightProgresses);
                weightProgressListView.setAdapter(adapter);
                for (DataSnapshot weight : snapshot.getChildren()) {
                    WeightProgress temp = weight.getValue(WeightProgress.class);
                    weightProgresses.add(temp);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(ACTIVITY_NAME, "Could not get exercise set reps", error.toException());
            }
        });

        setRepAddWeightProgressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightProgress newWeightProgress = new WeightProgress((Integer.parseInt(weightProgressEdit.getText().toString())), new Date());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                fAuth = FirebaseAuth.getInstance();
                userID = fAuth.getCurrentUser().getUid();

                DatabaseReference rootRef = database.getReference();
                DatabaseReference workoutsReference = rootRef.child("users").child(userID);

                Map<String,Object> childUpdates = new HashMap<>();

                String weightProgressKey = workoutsReference.push().getKey();

                childUpdates = new HashMap<>();
                newWeightProgress.setWeightProgressID(weightProgressKey);
                childUpdates.put("/userExercises/" + exerciseID + "/setRepConfigs/" + setRepID + "/weightProgressTracking/" + weightProgressKey, newWeightProgress);
                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + exerciseID + "/setRepConfigs/" + setRepID + "/weightProgressTracking/" + weightProgressKey, newWeightProgress);

                workoutsReference.updateChildren(childUpdates);

                adapter.notifyDataSetChanged();
            }
        });
    }

    private class WeightProgressAdapter extends ArrayAdapter<WeightProgress> {
        public WeightProgressAdapter(Context ctx, int resource, ArrayList<WeightProgress> weightProgressList) {
            super(ctx, resource,weightProgressList);
        }

        @Override
        public View getView(int position, View result, ViewGroup parent) {
            WeightProgress weightProgress = getItem(position);

            if (result == null) {
                result = LayoutInflater.from(getContext()).inflate(R.layout.activity_exercise_view_list_item, parent, false);
            }

            Date date = weightProgress.getProgressDate();
            SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");

            TextView weightTextView = result.findViewById(R.id.setCustomWeightAmount);
            TextView dateTextView = result.findViewById(R.id.setCustomDate);
            weightTextView.setText(Integer.toString(weightProgress.getWeight()));
            dateTextView.setText(formattedDate.format(date));

            return result;
        }
    }
}