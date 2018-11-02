package MedicalReview;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;

public class ReviewData {
    private Factory factory;

    public ReviewData() {
        factory = Factory.Get_Factory();
    }

    //should load and return the data
    public static Map<String, String> loadData() {
        Map<String, String> data = new HashMap<>();

        //read in the data
        //XML_READER_FROM_FACTORY reader = factory.Get_File_Reader();
        String tmp;// = reader.getRecentData();
        /*String[] lines = tmp.split(":");    //colon separated by entry time
        for (String line : lines) {
            String[] attribute = line.split(",");   //comma separated for attribute/value pair
            for (String values : attribute) {
                String[] last = values.split("-");  //will split the attribute name and value
            }
        }*/


        return data;
    }

    //LOADS THE DATASETS FROM FILE TO BE GRAPHED
    public void loadReviews() {

    }

    //SAVES THE DATASETS TO FILE FOR LATER ACCESS
    public void saveReviews(DailyReview toSave) {

    }
}
