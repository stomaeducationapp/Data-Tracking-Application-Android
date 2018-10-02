package MedicalReview;

import java.util.HashMap;
import java.util.Map;

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
        Map<Long, Integer> data = new HashMap<>();
        yesterday = new DailyReview(today);

        today = new DailyReview();

        //Read in the account data to create the graphs.
        data = ReviewData.loadData();

        today.generateGraph(data, TYPE.STATE);
        today.generateGraph(data, TYPE.OUTPUT);

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

        return true;
    }

    public boolean dismissReview() {
        boolean success = true;

        return true;
    }
}
