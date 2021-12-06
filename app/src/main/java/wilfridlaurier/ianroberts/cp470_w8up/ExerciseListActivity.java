package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/*

    This is the Exercise List Activity. It will allow the user to see a list of all the exercises
    so that they can add them to their workout.
    In addition it will:
        - Give the user the option to search through the list of workouts to add one
        - Give the user the option to filter by muscle group / custom workouts
        - Give the user the option to create their own custom exercise which will send them
        to the ExerciseCreateActivity

 */

public class ExerciseListActivity extends AppCompatActivity {

    Button createCustomExerciseButton;

    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        createCustomExerciseButton = findViewById(R.id.createCustomExerciseButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        DatabaseReference rootRef = database.getReference();
        DatabaseReference exercisesReference = rootRef.child("users").child(userID).child("userExercises");

        SetRep testSetRepOne = new SetRep(3,10,350);
        Exercise testExerciseOne = new Exercise("ExerciseTestOne",testSetRepOne,"Chest");

        SetRep testSetRepTwo = new SetRep(3,10,350);
        Exercise testExerciseTwo = new Exercise("ExerciseTestTwo",testSetRepTwo,"Back");

        ArrayList<Exercise> userExercises = new ArrayList<>();
        userExercises.add(testExerciseOne);
        userExercises.add(testExerciseTwo);

        exercisesReference.setValue(userExercises);

        createCustomExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToCreateCustomExercise = new Intent(ExerciseListActivity.this, ExerciseCreateActivity.class);
                startActivity(goToCreateCustomExercise);
            }
        });
    }
}