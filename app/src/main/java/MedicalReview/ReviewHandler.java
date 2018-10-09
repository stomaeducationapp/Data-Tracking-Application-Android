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
        Map<String, String> data = new HashMap<>();

        //Read in the account data to create the graphs.
        data = ReviewData.loadData();

        if (control == 0) { //must be the first time
            yesterday = new DailyReview();
            today = new DailyReview();

            today.calcStateGraph(parseData(data, TYPE.STATELINE));
            today.calcStateChart(parseData(data, TYPE.STATEPIE));
            today.calcVolumeGraph(parseData(data, TYPE.VOLUMELINE));
            today.calcBagGraph(parseData(data, TYPE.BAGBAR));
            today.calcWellbeingChart(parseData(data, TYPE.WELLBEING));

            //since there is no yesterday yet, just make it the same as today
            yesterday = new DailyReview(today);

            control = 1;
        }
        else if (control == 1) {    //any subsequent call
            //set the current review to the yesterday variable
            yesterday = new DailyReview(today);

            //calculate the new graph
            today.calcStateGraph(parseData(data, TYPE.STATELINE));
            today.calcStateChart(parseData(data, TYPE.STATEPIE));
            today.calcVolumeGraph(parseData(data, TYPE.VOLUMELINE));
            today.calcBagGraph(parseData(data, TYPE.BAGBAR));
            today.calcWellbeingChart(parseData(data, TYPE.WELLBEING));
        }
        else {
            success = false;
        }

        return success;
    }

    public boolean selectReview(DAY day, TYPE choice) {
        boolean success = true;
        //creates the activity to display the chart
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


    /*
    This is temporary. The logic will need to change depending on structure of the input data
    BIG ASSUMPTION FOR NOW - Map being read from file will have k=Attribute and v=Date in millis,Value as strings
     */
    public Map<Date, Integer> parseData(Map<String, String> temp, TYPE type) {
        Map<Date, Integer> ret = new HashMap<>();

        //gets ALL data in type value pairs
        String[] attributes = temp.keySet().toArray(new String[0]);

        if (type == TYPE.STATELINE || type == TYPE.STATEPIE) {  //only want the state entries
            for (String name: attributes) {
                if (name.contains("state")) {
                    String[] elements = temp.get(name).split(",");
                    ret.put(new Date(Long.parseLong(elements[0])), Integer.parseInt(elements[1]));
                }
            }
        }
        else if (type == TYPE.VOLUMELINE || type == TYPE.BAGBAR) {  //only want output entries
            for (String name: attributes) {
                if (name.contains("output")) {
                    String[] elements = temp.get(name).split(",");
                    ret.put(new Date(Long.parseLong(elements[0])), Integer.parseInt(elements[1]));
                }
            }
        }
        else if (type == TYPE.WELLBEING) {  //only want wellbeing entries
            for (String name: attributes) {
                if (name.contains("wellbeing")) {
                    String[] elements = temp.get(name).split(",");
                    ret.put(new Date(Long.parseLong(elements[0])), Integer.parseInt(elements[1]));
                }
            }
        }
        return ret;
    }
}
