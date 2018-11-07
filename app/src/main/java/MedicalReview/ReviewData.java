package MedicalReview;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import XML.Medical_Reader;

public class ReviewData {
    private Factory factory;

    /**
     * Default constructor for the object.
     * Initialises the factory.
     */
    public ReviewData() {
        factory = Factory.Get_Factory();
    }

    /**
     * Handles the call to the file reader to get the data to calculate the DailyReview.
     * Expects to receive data in specific format "TIME:attribute1-value1,attribute2-value2,etc"
     * Parses the data returned from file into the expected map.
     * File that is being read from should only contain the entries to be used in this review.
     * @return data, the map containing the most recent 24 hours data.
     */
    public Map<String, String> loadData() {
        Map<String, String> data = new HashMap<>();

        //TODO: Check compatibility with reader when factory methods implemented
        //read in the data - should have 1 entry per line
        /*Medical_Reader reader = factory.Make_Medical_Reader();

        //TODO: Uncomment the correct file return and remove the other
        //If data is one string
        //String tmp = reader.Read_File();
        //String[] lines = tmp.split("/n");

        //If data comes back as a map. k=entryNum, v="DATE:ATTRIBUTE-VALUE"
        //Map<String, String> tmp = reader.Read_File();
        //String[] lines = tmp.keySet().toArray(new String[0]);

        for (String line : lines) {
            String[] entry = line.split(":");   //extract the time
            String[] attributes = entry[1].split(",");   //comma separated for attribute/value pair
            for (String values : attributes) {
                String[] last = values.split("-");  //will split the attribute name and value
                data.put(last[0], entry[0]+","+last[1]);    //k=attribute, v=time,value
            }
        }*/

        return data;
    }


    /*
     * THESE METHODS CURRENTLY HAVE NO PURPOSE, ARE HERE FOR FUTURE IMPLEMENTATION
     */

    /**
     * NOTE: This method is currently unused. Can be implemented further in future if loading is
     * deemed necessary.
     * Loads today and yesterday DailyReview object state from file.
     * @return sets, array containing the two DailyReview objects.
     */
    public DailyReview[] loadReviews() {
        DailyReview[] sets = new DailyReview[2];

        //load the reviews from the file system
        //sets[0] = new DailyReview(reader.getTodayDataset());
        //sets[1] = new DailyReview(reader.getYesterdayDataset());

        return sets;
    }

    /**
     * NOTE: This method is currently unused. Can be implemented further in future if saving is
     * deemed necessary.
     * Saves today and yesterday DailyReview object state to file.
     * @param toSave, array containing the DailyReview objects to save.
     */
    public void saveReviews(DailyReview[] toSave) {
        //XML_WRITER_FROM_FACTORY writer = factory.Get_File_Writer();
        //writer.saveDataset("today", toSave[0]);
        //writer.saveDataset("yesterday", toSave[1]);
    }
}
