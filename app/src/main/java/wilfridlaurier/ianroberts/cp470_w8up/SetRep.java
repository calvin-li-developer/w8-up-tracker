package wilfridlaurier.ianroberts.cp470_w8up;

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

