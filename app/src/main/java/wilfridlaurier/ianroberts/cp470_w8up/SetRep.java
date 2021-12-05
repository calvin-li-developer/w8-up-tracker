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
    int sets = 0;
    int reps = 0;
    int weight = 0;

    public SetRep(int sets, int reps, int weight){
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
    }

    // Set methods for the SetRep class
    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    //Get methods for the SetRep class
    public int getSets() {
        return sets;
    }

    public int getSReps() {
        return reps;
    }

    public int getWeight() {
        return weight;
    }
}

