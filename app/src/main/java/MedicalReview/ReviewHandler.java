package MedicalReview;

import android.content.Context;
import android.content.Intent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import capstonegroup2.dataapp.DailyReviewGraph;

/**
 * Class: ReviewHandler
 * Purpose: Handles calls to supporting classes to create and maintain the daily review package
 * @author Ethan
 */
public class ReviewHandler {
    public enum TYPE {STATELINE, STATEPIE, VOLUMELINE, BAGBAR, WELLBEING}

    private Factory factory;
    private DailyReview today;
    private DailyReview yesterday;
    private int control;    //determine if it is the first time the graphs are calculated

    /**
     * Default constructor, sets class fields to default values.
     */
    public ReviewHandler() {
        factory = Factory.Get_Factory();
        yesterday = null;
        today = null;
        control = 0;
    }

    /**
     * Handles creating the data reader, then passing this on to the generate review method
     * @return true if the method succeeds or false otherwise.
     */
    public boolean generateReview() {
        boolean success = true;
        ReviewData loader = factory.Make_Review_Data_Reader();
        Map<String, String> data;

        //Read in the account data to create the graphs.
        data = loader.loadData();

        if(!newReview(data)) {
            success = false;
        }

        return success;
    }

    /**
     * generateReview handles the creation and maintaining of DailyReview objects. If the call is
     * the first instance of this object, the today and yesterday object will be the same, as there
     * is only one days worth of data. Any subsequent calls handle making the 'today' object the
     * 'yesterday' object and calculating the new 'today' instance.
     * @return success, a boolean representing whether the method succeeded or failed.
     */
    public boolean newReview(Map<String, String> data) {
        boolean success = true;

        if (control == 0) { //must be the first time the object has been used
            yesterday = factory.Make_Review_Dataset();
            today = factory.Make_Review_Dataset();

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
            yesterday = factory.Make_Review_Dataset(today);

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

    //returns the intent to launch the graph activity from with the data

    /**
     * Creates the intent to launch the DailyReviewGraph activity. Packages the 'today' and 'yesterday'
     * variables as part of the intent so the activity can use this data.
     * @param context the context of the calling activity.
     * @return i the intent to be used to launch the DailyReviewGraph activity.
     */
    public Intent getViewIntent(Context context) {
        //creates the activity to display the chart
        Intent i = new Intent(context, DailyReviewGraph.class);

        i.putExtra("today", today);
        i.putExtra("yesterday", yesterday);

        return i;
    }

    /*
    This is temporary. The logic will need to change depending on structure of the input data
    BIG ASSUMPTION FOR NOW - Map being read from file will have k=Attribute and v=DateTime in millis,Value as strings
     */
    /**
     * Parses the data read from file to split into each attribute. Since we use different attributes
     * for each graph, should limit the data to only relevant values.
     * @param temp map containing the full data set.
     * @param type which graph type is the new data set targeted to. Determines what attribute to
     *             look for.
     * @return ret the map containing only one type of attribute.
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
                    if (elements[1].equals("good")){
                        //Value of 1 for good
                        ret.put(new Date(Long.parseLong(elements[0])), 1);
                    }
                    else if (elements[1].equals("bad")) {
                        //Value of 0 for bad
                        ret.put(new Date(Long.parseLong(elements[0])), 0);
                    }
                }
            }
        }
        return ret;
    }
}
