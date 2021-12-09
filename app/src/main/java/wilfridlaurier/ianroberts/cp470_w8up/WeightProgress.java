package wilfridlaurier.ianroberts.cp470_w8up;

import java.util.Date;

public class WeightProgress {
    String weightProgressID = "";
    int weight = 0;
    Date progressDate = new Date();

    public WeightProgress(){
        this.weightProgressID = "";
        this.weight = 0;
        this.progressDate = new Date();
    }

    public WeightProgress(int weight, Date date){
        this.weightProgressID = "";
        this.weight = weight;
        this.progressDate = new Date();
    }
    public WeightProgress(String weightProgressID, int weight, Date date){
        this.weightProgressID = weightProgressID;
        this.weight = weight;
        this.progressDate = new Date();
    }

    public String getWeightProgressID() {
        return weightProgressID;
    }

    public int getWeight() {
        return weight;
    }

    public Date getProgressDate() {
        return progressDate;
    }

    public void setWeightProgressID(String weightProgressID) {
        this.weightProgressID = weightProgressID;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProgressDate(Date progressDate) {
        this.progressDate = progressDate;
    }
}
