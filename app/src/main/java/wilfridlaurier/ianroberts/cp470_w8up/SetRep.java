package wilfridlaurier.ianroberts.cp470_w8up;

/*

    This is the SetRep Object. It has these fields:

        String setsAndReps - A string in the format sxr where s and r are sets and reps. ex. 3x10
        for 3 sets of 10 reps

        int weight - for how much weight to do for each rep

    It has the methods:

        Constructor method for object creation

        Get/Set methods for all fields

 */

public class SetRep {
    String setRepID = "";
    int sets = 0;
    int reps = 0;
    WeightProgress weightProgress = new WeightProgress();

    public SetRep(){
        this.sets = 0;
        this.reps = 0;
        this.weightProgress = new WeightProgress();
    }

    public SetRep(int sets, int reps, WeightProgress weightProgress){
        this.sets = sets;
        this.reps = reps;
        this.weightProgress = weightProgress;
    }

    // Set methods for the SetRep class

    public void setSetRepID(String setRepID) {
        this.setRepID = setRepID;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeightProgress(WeightProgress weightProgress) {
        this.weightProgress = weightProgress;
    }

    //Get methods for the SetRep class

    public String getSetRepID() {
        return setRepID;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public WeightProgress getWeightProgress() {
        return weightProgress;
    }
}

