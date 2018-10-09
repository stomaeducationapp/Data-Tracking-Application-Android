package MedicalReview;

import android.content.Intent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import capstonegroup2.dataapp.DailyReviewGraph;

public class ReviewHandler {
    private enum DAY {TODAY, YESTERDAY}
    public enum TYPE {STATELINE, STATEPIE, VOLUMELINE, BAGBAR, WELLBEING}

    private DailyReview today;
    private DailyReview yesterday;
    private int control;    //determine if it is the first time the graphs are calculated

    public ReviewHandler() {
        yesterday = null;
        today = null;
        control = 0;
    }

    //moves the current review into the yesterday var. Generates today's review
    public boolean generateReview() {
        boolean success = true;
        Map<Date, Integer> data = new HashMap<>();

        //Read in the account data to create the graphs.
        data = ReviewData.loadData();

        if (control == 0) { //must be the first time
            yesterday = new DailyReview();
            today = new DailyReview();

            today.calcStateGraph(data);
            today.calcStateChart(data);
            today.calcVolumeGraph(data);
            today.calcBagGraph(data);
            today.calcWellbeingChart(data);

            //since there is no yesterday yet, just make it the same as today
            yesterday = new DailyReview(today);

            control = 1;
        }
        else if (control == 1) {    //any subsequent call
            //set the current review to the yesterday variable
            yesterday = new DailyReview(today);

            //calculate the new graph
            today.calcStateGraph(data);
            today.calcStateChart(data);
            today.calcVolumeGraph(data);
            today.calcBagGraph(data);
            today.calcWellbeingChart(data);
        }
        else {
            success = false;
        }

        return success;
    }

    public boolean selectReview(DAY day, TYPE choice) {
        boolean success = true;
        DailyReviewGraph view = new DailyReviewGraph();

        if (day == DAY.TODAY) {  //display the graph from today
            switch (choice) {
                case STATELINE:
                    view.displayGraph(today, TYPE.STATELINE);
                    break;
                case STATEPIE:
                    view.displayGraph(today, TYPE.STATEPIE);
                    break;
                case VOLUMELINE:
                    view.displayGraph(today, TYPE.VOLUMELINE);
                    break;
                case BAGBAR:
                    view.displayGraph(today, TYPE.BAGBAR);
                    break;
                case WELLBEING:
                    view.displayGraph(today, TYPE.WELLBEING);
                    break;
            }
        }
        else if (day == DAY.YESTERDAY) { //display the graph for yesterday
            switch (choice) {
                case STATELINE:
                    view.displayGraph(yesterday, TYPE.STATELINE);
                    break;
                case STATEPIE:
                    view.displayGraph(yesterday, TYPE.STATEPIE);
                    break;
                case VOLUMELINE:
                    view.displayGraph(yesterday, TYPE.VOLUMELINE);
                    break;
                case BAGBAR:
                    view.displayGraph(yesterday, TYPE.BAGBAR);
                    break;
                case WELLBEING:
                    view.displayGraph(yesterday, TYPE.WELLBEING);
                    break;
            }
        }
        else {
            success = false;
        }
        return success;
    }

    public boolean dismissReview() {
        boolean success = true;

        return success;
    }
}
