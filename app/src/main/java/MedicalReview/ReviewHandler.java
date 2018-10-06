package MedicalReview;

import android.content.Intent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import capstonegroup2.dataapp.DailyReviewGraph;

public class ReviewHandler {
    private enum DAY {TODAY, YESTERDAY}
    public enum TYPE {STATE, OUTPUT}

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

    public boolean selectReview(DAY choice) {
        boolean success = true;

        if (choice == DAY.TODAY) {  //display the graph from today
            today.display();
        }
        else if (choice == DAY.YESTERDAY) { //display the graph for yesterday
            yesterday.display();
        }

        DailyReviewGraph view = new DailyReviewGraph();

        today.displayStateGraph(view);

        return true;
    }

    public boolean dismissReview() {
        boolean success = true;

        return true;
    }
}
