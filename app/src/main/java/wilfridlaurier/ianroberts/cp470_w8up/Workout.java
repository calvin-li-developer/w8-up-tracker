package wilfridlaurier.ianroberts.cp470_w8up;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/*

    This is the Workout Object. It has these fields:

        String workoutName - Holds the name of the workout

        ArrayList<Exercise> exerciseList - which is an arraylist of Exercise objects

        Boolean isPinned - telling you whether or not to keep these workouts at the top of the list

        Date createdDate - used for sorting of the workouts newest to oldest

        Date modifiedDate - used for sorting of the workouts by last modified

        ArrayList<String> muscleGroupCategories - which is an arraylist of strings that say which
        muscle groups are included in this workout

    It has the methods:

        Constructor method for creating a workout object

        Get/Set methods for all relevant fields

        2 additional methods for adding or removing exercises from the workout

 */

// TODO move this to the workouts activity

public class Workout {
    String workoutName = "";
    ArrayList<Exercise> exerciseList = new ArrayList<>();
    Boolean isPinned = false;
    Date createdDate = new Date();
    Date lastModified = new Date();
    ArrayList<String> muscleGroupCategories = new ArrayList<>();

    // Constructor for the workout method
    public Workout(){
        this.workoutName = "";
        this.muscleGroupCategories = new ArrayList<>();
    }
    public Workout(String workoutName, ArrayList<String> muscleGroupCategories){
        this.workoutName = workoutName;
        this.muscleGroupCategories = muscleGroupCategories;
    }

    // Set methods for the workout class
    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
        lastModified = new Date();
    }
    public void setPinned(Boolean pinned) {
        isPinned = pinned;
        lastModified = new Date();
    }

    public void setMuscleGroupCategories(ArrayList<String> muscleGroupCategories) {
        this.muscleGroupCategories = muscleGroupCategories;
        lastModified = new Date();
    }

    // Get methods for the workout class
    public String getWorkoutName() {
        return workoutName;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public Boolean getPinned() {
        return isPinned;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public ArrayList<String> getMuscleGroupCategories() {
        return muscleGroupCategories;
    }

    // Additional Methods
    public Boolean addExercise(Exercise newExercise){
        if (!this.exerciseList.contains(newExercise)) {
            this.exerciseList.add(newExercise);
            lastModified = new Date();
            return true;
        }
        return false;
    }

    public Boolean removeExercise(Exercise exercise){
        if (this.exerciseList.contains(exercise)) {
            this.exerciseList.remove(exercise);
            lastModified = new Date();
            return true;
        }
        return false;
    }

    public int getNumberOfExercises() {
        return exerciseList.size();
    }
}


