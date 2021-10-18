package wilfridlaurier.ianroberts.cp470_w8up;

import android.media.Image;

import java.util.ArrayList;

public class Exercise {
    String exerciseName = "";
    ArrayList<SetRep> setRepConfigs = new ArrayList<>();
    String muscleGroupCategory = "";
    Image exerciseImage;

    // TODO sort out how to do images for exercises

    public Exercise(String exerciseName, SetRep setRep, String muscleGroupCategory){
        this.exerciseName = exerciseName;
        this.setRepConfigs.add(setRep);
        this.muscleGroupCategory = muscleGroupCategory;
        //this.exerciseImage =
    }
    public Exercise(String exerciseName, SetRep setRep, String muscleGroupCategory,Image exerciseImage){
        this.exerciseName = exerciseName;
        this.setRepConfigs.add(setRep);
        this.muscleGroupCategory = muscleGroupCategory;
        //this.exerciseImage = exerciseImage;
    }

    // Set methods for Exercise class

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setMuscleGroupCategory(String muscleGroupCategory) {
        this.muscleGroupCategory = muscleGroupCategory;
    }

    public void setExerciseImage(Image exerciseImage) {
        this.exerciseImage = exerciseImage;
    }

    // Get methods for Exercise class

    public String getExerciseName() {
        return exerciseName;
    }

    public ArrayList<SetRep> getSetRepConfigs() {
        return setRepConfigs;
    }

    public String getMuscleGroupCategory() {
        return muscleGroupCategory;
    }

    public Image getExerciseImage() {
        return exerciseImage;
    }

    // Additional methods

    public Boolean addSetRep(SetRep newSetRep){
        if (!this.setRepConfigs.contains(newSetRep)) {
            this.setRepConfigs.add(newSetRep);
            return true;
        }
        return false;
    }

    public Boolean removeSetRep(SetRep setRep){
        if (this.setRepConfigs.contains(setRep)) {
            this.setRepConfigs.remove(setRep);
            return true;
        }
        return false;
    }
}
