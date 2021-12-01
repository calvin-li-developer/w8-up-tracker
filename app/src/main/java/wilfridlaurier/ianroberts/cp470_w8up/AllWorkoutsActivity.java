package wilfridlaurier.ianroberts.cp470_w8up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

// TODO Update naming conventions

/*
    Naming. WorkoutsActivity                -> AllWorkoutsActivity     -> Shows list of all workouts
            RoutineActivity                 -> WorkoutViewActivity     -> Shows selected Workout
            CreateNewRoutineActivity        -> WorkoutCreateActivity   -> Allows for workout creation
            ExerciseOptionsActivity         -> ExerciseListActivity    -> Used when adding exercise to a workout
            ViewExerciseActivity            -> ExerciseViewActivity    -> Shows exercise weights/reps etc.
            CreateCustomExerciseActivity    -> ExerciseCreateActivity  -> Allows for custom exercise creation
            no previous file                -> ExerciseEditActivity    -> Allows user to edit an exercise
 */

/*

    This is the Workouts Activity. It will present the user with a list of their custom workouts.
    In addition it will:
        - Give the user the option to create a new custom workout that will send them to the
        WorkoutCreateActivity
        - Give the user the option to select a workout which will send them to the WorkoutViewActivity
        - Give them the option to delete a workout
        - The user will be able to search these workouts as well as filter by muscle group

 */

public class AllWorkoutsActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "WorkoutsActivity";

    SearchView workoutsSearchView;
    ImageButton wnewWorkoutButton;
    ImageButton filterWorkoutsButton;
    ListView workoutsListView;
    ArrayAdapter adapter;
    // TODO update this to be a list of workout objects?
    // ArrayList<String> list1;
    ArrayList<Workout> userWorkouts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_workouts);
        workoutsSearchView = findViewById(R.id.searchWorkoutsSearchView);
        wnewWorkoutButton = findViewById(R.id.newWorkoutButton);
        filterWorkoutsButton = findViewById(R.id.filterWorkoutsButton);
        workoutsListView = findViewById(R.id.workoutsListView);

        userWorkouts = new ArrayList<>();
        // TODO get the list of workouts for the user from the database and put it into the arraylist

        // Below is test code for searching and filtering the list
//        list1 = new ArrayList<>();
//
//        list1.add("Monday");
//        list1.add("Tuesday");
//        list1.add("Wednesday");
//        list1.add("Thursday");
//        list1.add("Friday");
//        list1.add("Saturday");
//        list1.add("Sunday");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userWorkouts);
        workoutsListView.setAdapter(adapter);
        workoutsSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        wnewWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked add new workout");
                Intent newWorkoutIntent = new Intent(getApplicationContext(), WorkoutCreateActivity.class);
                startActivity(newWorkoutIntent);
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

    // TODO adapt this to work correctly for workouts
//    private class WorkoutsAdapter extends ArrayAdapter<String>{
//        public WorkoutsAdapter(Context ctx) {
//            super(ctx, 0);
//        }
//
//        public int getCount() {
//            return userWorkouts.size();
//        }
//
//        // TODO update this so that you can search by something other than position?
//        public String getItem(int position) {
//            return userWorkouts.get(position);
//        }
//
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = WorkoutsActivity.this.getLayoutInflater();
//
//            //This will recreate your View that you made in the resource file. If the position is an even number (position%2 == 0), then inflate chat_row_incoming, else inflate chat_row_outgoing:
//
//            View result = null ;
//
//            // TODO maybe only need one result statment because we are not changing the layout back and forth?
//            if(position%2 == 0)
//                // TODO create layout for how we want the workouts to look
//                //result = inflater.inflate(R.layout.chat_row_incoming, null);
//
//            else
//
//                //result = inflater.inflate(R.layout.chat_row_outgoing, null);
//
//            //From the resulting view, get the TextView which holds the string message:
//
//            // TODO update this / maybe just remove because we are not dynamically updating these in realtime
//            //TextView message = (TextView)result.findViewById(R.id.message_text);
//            message.setText(getItem(position)); // get the string at position
//            return result;
//        }
//
//
//    }
}