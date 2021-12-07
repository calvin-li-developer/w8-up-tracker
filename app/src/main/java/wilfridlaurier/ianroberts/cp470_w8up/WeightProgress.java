package wilfridlaurier.ianroberts.cp470_w8up;

import java.util.Date;

public class WeightProgress {
    int weight = 0;
    Date progressDate = new Date();

    public WeightProgress(){

    }

    public WeightProgress(int weight, Date date){
        this.weight = 0;
        this.progressDate = new Date();
    }

    public int getWeight() {
        return weight;
    }

    public Date getProgressDate() {
        return progressDate;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setProgressDate(Date progressDate) {
        this.progressDate = progressDate;
    }
}
