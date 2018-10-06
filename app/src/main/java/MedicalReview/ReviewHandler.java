package MedicalReview;

import android.content.Intent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import capstonegroup2.dataapp.DailyReviewGraph;

public class ReviewHandler {
    private enum DAY {TODAY, YESTERDAY}
    private enum TYPE {STATELINE, STATEPIE, VOLUMELINE, BAGBAR, WELLBEING}

    private DailyReview today;
    private DailyReview yesterday;

    public ReviewHandler() {
        today = null;
        yesterday = null;
    }

    //moves the current review into the yesterday var. Generates today's review
    public boolean generateReview() {
        boolean success = true;
        Map<Date, Integer> data = new HashMap<>();
        yesterday = new DailyReview();
        today = new DailyReview();

        //Read in the account data to create the graphs.
        data = ReviewData.loadData();

        today.calcStateGraph(data);
        today.calcStateChart(data);
        today.calcVolumeGraph(data);
        today.calcBagGraph(data);
        today.calcWellbeingChart(data);

        if (yesterday == null) {    //has not yet been set
            yesterday = new DailyReview(today);
        }

        return true;
    }

    public boolean selectReview(DAY day, TYPE choice) {
        boolean success = true;
        DailyReviewGraph view = new DailyReviewGraph();

        if (day == DAY.TODAY) {  //display the graph from today
            switch (choice) {
                case STATELINE:
                    today.displayStateGraph(view);
                    break;
                case STATEPIE:
                    today.displayStateChart(view);
                    break;
                case VOLUMELINE:
                    today.displayVolumeGraph(view);
                    break;
                case BAGBAR:
                    today.displayBagGraph(view);
                    break;
                case WELLBEING:
                    today.displayWellbeingChart(view);
                    break;
            }
        }
        else if (day == DAY.YESTERDAY) { //display the graph for yesterday
            switch (choice) {
                case STATELINE:
                    yesterday.displayStateGraph(view);
                    break;
                case STATEPIE:
                    yesterday.displayStateChart(view);
                    break;
                case VOLUMELINE:
                    yesterday.displayVolumeGraph(view);
                    break;
                case BAGBAR:
                    yesterday.displayBagGraph(view);
                    break;
                case WELLBEING:
                    yesterday.displayWellbeingChart(view);
                    break;
            }
        }
        return true;
    }

    public boolean dismissReview() {
        boolean success = true;

        return true;
    }
}
