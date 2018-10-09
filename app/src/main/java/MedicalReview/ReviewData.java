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


        return data;
    }

    public void loadReviews() {

    }

    public void saveReviews(DailyReview toSave) {

    }
}
