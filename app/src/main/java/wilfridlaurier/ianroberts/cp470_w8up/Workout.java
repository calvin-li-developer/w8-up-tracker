package wilfridlaurier.ianroberts.cp470_w8up;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

// TODO move this to the workouts activity
enum MuscleGroup{
    CHEST,
    BACK,
    ARMS,
    ABDOMINALS,
    LEGS,
    SHOULDERS
}

public class Workout {
    String workoutName = "";
    ArrayList<Exercise> exerciseList = new ArrayList<>();
    Boolean isPinned = false;
    Date createdDate = new Date();
    Date lastModified = new Date();
    ArrayList<String> muscleGroupCategories = new ArrayList<>();

    // Constructor for the workout method
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

    public int getNumberOfExercises() {
        return exerciseList.size();
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
}


