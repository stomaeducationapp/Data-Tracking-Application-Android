package medical_review_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import MedicalReview.DailyReview;
import capstonegroup2.dataapp.DailyReviewGraph;

public class Daily_Review_Test extends AppCompatActivity {

    //@Test
    public void activate() {
        Intent i = new Intent(getBaseContext(), DailyReviewGraph.class);

        DailyReview todayData = getToday();
        DailyReview yesterdayData = getYesterday();

        i.putExtra("today", todayData);
        i.putExtra("yesterday", yesterdayData);

        //startActivity(i);
    }

    public DailyReview getToday(){
        DailyReview tmp = new DailyReview();

        //CREATE ALL DATASETS
        //STATE DATASET
        Map<Date, Integer> stateData = new HashMap<>();
        stateData.put(new Date(1540027800), 3);
        stateData.put(new Date(1540033200), 5);
        stateData.put(new Date(1540045530), 6);
        stateData.put(new Date(1540057395), 4);
        stateData.put(new Date(1540070024), 2);


        //VOLUME DATASET
        Map<Date, Integer> volumeData = new HashMap<>();
        volumeData.put(new Date(1540027800), 400);
        volumeData.put(new Date(1540045530), 450);
        volumeData.put(new Date(1540070024), 300);

        //WELLBEING DATASET
        Map<Date, Integer> wellbeingData = new HashMap<>();
        wellbeingData.put(new Date(1540027800), 1);
        wellbeingData.put(new Date(1540033200), 1);
        wellbeingData.put(new Date(1540045530), 0);
        wellbeingData.put(new Date(1540057395), 1);
        wellbeingData.put(new Date(1540070024), 1);


        tmp.calcStateGraph(stateData);
        tmp.calcStateChart(stateData);
        tmp.calcVolumeGraph(volumeData);
        tmp.calcBagGraph(volumeData);
        tmp.calcWellbeingChart(wellbeingData);

        return tmp;
    }

    public DailyReview getYesterday(){
        DailyReview tmp = new DailyReview();

        //CREATE ALL DATASETS
        //STATE DATASET
        Map<Date, Integer> stateData = new HashMap<>();
        stateData.put(new Date(1539940844), 6);
        stateData.put(new Date(1539949223), 7);
        stateData.put(new Date(1539955222), 7);
        stateData.put(new Date(1539971162), 5);
        stateData.put(new Date(1539988252), 4);


        //VOLUME DATASET
        Map<Date, Integer> volumeData = new HashMap<>();
        stateData.put(new Date(1539940844), 300);
        stateData.put(new Date(1539949223), 400);
        stateData.put(new Date(1539955222), 300);
        stateData.put(new Date(1539988252), 450);

        //WELLBEING DATASET
        Map<Date, Integer> wellbeingData = new HashMap<>();
        stateData.put(new Date(1539940844), 0);
        stateData.put(new Date(1539949223), 0);
        stateData.put(new Date(1539955222), 0);
        stateData.put(new Date(1539971162), 1);
        stateData.put(new Date(1539988252), 1);


        tmp.calcStateGraph(stateData);
        tmp.calcStateChart(stateData);
        tmp.calcVolumeGraph(volumeData);
        tmp.calcBagGraph(volumeData);
        tmp.calcWellbeingChart(wellbeingData);

        return tmp;
    }
}
