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
    String setsAndReps = "";
    int weight = 0;

    public SetRep(String setsAndReps, int weight){
        this.setsAndReps = setsAndReps;
        this.weight = weight;
    }

    // Set methods for the SetRep class
    public void setSetsAndReps(String setsAndReps) {
        this.setsAndReps = setsAndReps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    //Get methods for the SetRep class
    public String getSetsAndReps() {
        return setsAndReps;
    }

    public int getWeight() {
        return weight;
    }
}

