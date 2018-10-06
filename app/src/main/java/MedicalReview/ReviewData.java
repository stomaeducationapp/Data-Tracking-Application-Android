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
    public static Map<Date, Integer> loadData() {
        Map<Date, Integer> data = new HashMap<>();

        return data;
    }

    public void loadReviews() {

    }

    public void saveReviews(DailyReview_Old toSave) {

    }
}
