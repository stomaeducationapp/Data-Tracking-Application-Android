package MedicalReview;

import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
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

        //TODO POSSIBLE REWORK SINCE REVIEW OBJECTS MAY BE LOST ON APP CLOSE
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
        int day, month, year, hour; //Integers we will use to create a entry time from string we retrieve from file
        int min = 0; //As of now we do not store the minutes recorded - TODO CONSIDER ADDING THIS TO FILE

        //TODO REQORK WE FILE READ SUCCESS TO HANDLE READING IN ORDER IF THE POINTS ARE OUT OF WACK

        //gets ALL data in type value pairs
        String[] attributes = temp.keySet().toArray(new String[0]);

        if (type == TYPE.STATELINE || type == TYPE.STATEPIE) {  //only want the state entries
            for (String name: attributes) {
                if (name.contains("Medical_State")) {
                    //Lets find the date associated with this entry
                    String[] dElements = name.split("-"); //Since multiple entries have been retrieved - there is a -X indicating the number of the entry
                    String numEntry =  dElements[1]; //Should always be second entry due to our xml structure
                    String dateValue = temp.get( ("Entry_Time-" + numEntry) ); //Extract the date string
                    dElements = dateValue.split("-"); //Split the date string into the individual numbers
                    hour = Integer.parseInt(dElements[0]);
                    day = Integer.parseInt(dElements[1]);
                    month = Integer.parseInt(dElements[2]);
                    year = Integer.parseInt(dElements[3]);
                    Calendar c = Calendar.getInstance();
                    //TODO TEST WHETHER NEED + 1990 IN YEAR
                    c.set(year, month, day, hour, min); //We need to do this variant since older versions of creating Date objects using this method are deprecated

                    String value = temp.get(name); //grab the value associated with our key
                    ret.put(c.getTime(), Integer.parseInt(value));
                }
            }
        }
        else if (type == TYPE.VOLUMELINE || type == TYPE.BAGBAR) {  //only want output entries
            for (String name: attributes) {
                if (name.contains("Volume")) {
                    //Lets find the date associated with this entry
                    String[] dElements = name.split("-"); //Since multiple entries have been retrieved - there is a -X indicating the number of the entry
                    String numEntry =  dElements[1]; //Should always be second entry due to our xml structure
                    String dateValue = temp.get( ("Entry_Time-" + numEntry) ); //Extract the date string
                    dElements = dateValue.split("-"); //Split the date string into the individual numbers
                    hour = Integer.parseInt(dElements[0]);
                    day = Integer.parseInt(dElements[1]);
                    month = Integer.parseInt(dElements[2]);
                    year = Integer.parseInt(dElements[3]);
                    Calendar c = Calendar.getInstance();
                    //TODO TEST WHETHER NEED + 1990 IN YEAR
                    c.set(year, month, day, hour, min); //We need to do this variant since older versions of creating Date objects using this method are deprecated

                    String value = temp.get(name); //grab the value associated with our key
                    ret.put(c.getTime(), Integer.parseInt(value));
                }
            }
        }
        else if (type == TYPE.WELLBEING) {  //only want wellbeing entries
            for (String name: attributes) {
                if (name.contains("Wellbeing")) {
                    //Lets find the date associated with this entry
                    String[] dElements = name.split("-"); //Since multiple entries have been retrieved - there is a -X indicating the number of the entry
                    String numEntry =  dElements[1]; //Should always be second entry due to our xml structure
                    String dateValue = temp.get( ("Entry_Time-" + numEntry) ); //Extract the date string
                    dElements = dateValue.split("-"); //Split the date string into the individual numbers
                    hour = Integer.parseInt(dElements[0]);
                    day = Integer.parseInt(dElements[1]);
                    month = Integer.parseInt(dElements[2]);
                    year = Integer.parseInt(dElements[3]);
                    Calendar c = Calendar.getInstance();
                    //TODO TEST WHETHER NEED + 1990 IN YEAR
                    c.set(year, month, day, hour, min); //We need to do this variant since older versions of creating Date objects using this method are deprecated

                    String value = temp.get(name); //grab the value associated with our key
                    //Temporary assumption that values for wellbeing are good (1) or bad (0)
                    if (value.equals("Good")){
                        ret.put(c.getTime(), 1);
                    }
                    else if (value.equals("Bad")) {
                        ret.put(c.getTime(), 0);
                    }
                }
            }
        }
        return ret;
    }
}
