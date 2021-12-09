package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExerciseViewSetRepActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_view_set_rep);

//        int weight = 0;
//        Date date = new Date();
//        SimpleDateFormat formattedDate = new SimpleDateFormat("MM/dd/yyyy");
//
//        exerciseAddWeightProgressButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WeightProgress newWeightProgress = new WeightProgress((Integer.parseInt(weightProgressEdit.getText().toString())), new Date());
//
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                fAuth = FirebaseAuth.getInstance();
//                userID = fAuth.getCurrentUser().getUid();
//
//                DatabaseReference rootRef = database.getReference();
//                DatabaseReference workoutsReference = rootRef.child("users").child(userID);
//
//                Map<String,Object> childUpdates = new HashMap<>();
//
//                String weightProgressKey = workoutsReference.push().getKey();
//
//                childUpdates = new HashMap<>();
//                newWeightProgress.setWeightProgressID(weightProgressKey);
//                setRepID = ((SetRep)setRepSpinner.getSelectedItem()).getSetRepID();
//                childUpdates.put("/userExercises/" + selectedExercise.getExerciseID() + "/setRepConfigs/" + setRepID + "/weightProgressTracking/" + weightProgressKey, newWeightProgress);
//                childUpdates.put("/userWorkouts/" + workoutID + "/exerciseList/" + selectedExercise.getExerciseID() + "/setRepConfigs/" + setRepID + "/weightProgressTracking/" + weightProgressKey, newWeightProgress);
//
//                workoutsReference.updateChildren(childUpdates);
//            }
//        });
    }
}