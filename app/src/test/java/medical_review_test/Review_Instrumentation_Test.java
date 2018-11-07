package medical_review_test;

import android.content.Intent;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowAbsSpinner;
import org.robolectric.shadows.ShadowApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import MedicalReview.DailyReview;
import capstonegroup2.dataapp.BuildConfig;
import capstonegroup2.dataapp.DailyReviewGraph;
import capstonegroup2.dataapp.R;

import static junit.framework.Assert.*;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class Review_Instrumentation_Test {
    private DailyReviewGraph activity;
    //Setup the activity
    @Before
    public void setup() throws Exception {
        Intent intent = new Intent(ShadowApplication.getInstance().getApplicationContext(), DailyReviewGraph.class);
        intent.putExtra("today", getTodayTestData());
        intent.putExtra("yesterday", getYesterdayTestData());
        activity = Robolectric.buildActivity(DailyReviewGraph.class, intent).create().get();
    }

    @Test
    public void Test_Not_Null() {
        assertNotNull(activity);
    }

    @Test
    public void Test_Spinner_Select() {
        Spinner spin = activity.findViewById(R.id.graphSpinner);
        ShadowAbsSpinner shadSpin = shadowOf(spin);

        //select first item
        spin.setSelection(0);
        assertEquals("State Graph", spin.getSelectedItem().toString());

        //select second item
        spin.setSelection(1);
        assertEquals("State Pie Chart", spin.getSelectedItem().toString());

        //select third item
        spin.setSelection(2);
        assertEquals("Volume Graph", spin.getSelectedItem().toString());

        //select fourth item
        spin.setSelection(3);
        assertEquals("Output Graph", spin.getSelectedItem().toString());

        //select fifth item
        spin.setSelection(4);
        assertEquals("Wellbeing Pie Chart", spin.getSelectedItem().toString());

        //ensure the graphview is set
        LinearLayout view = activity.findViewById(R.id.chartView);
        assertNotNull(view);
    }

    @Test
    public void Test_Radio_Select() {
        RadioButton todayBtn = activity.findViewById(R.id.todayButton);
        RadioButton yesterdayBtn = activity.findViewById(R.id.yesterdayButton);

        assertTrue(todayBtn.isChecked());
        assertFalse(yesterdayBtn.isChecked());

        yesterdayBtn.performClick();

        assertFalse(todayBtn.isChecked());
        assertTrue(yesterdayBtn.isChecked());

        LinearLayout view = activity.findViewById(R.id.chartView);

        assertNotNull(view);
    }

    private DailyReview getTodayTestData(){
        DailyReview tmp = new DailyReview();

        //CREATE ALL DATASETS
        //STATE DATASET
        Map<Date, Integer> stateData = new HashMap<>();
        stateData.put(new Date((long)1540027800*1000), 3);
        stateData.put(new Date((long)1540033200*1000), 5);
        stateData.put(new Date((long)1540045530*1000), 6);
        stateData.put(new Date((long)1540057395*1000), 4);
        stateData.put(new Date((long)1540070024*1000), 2);

        Date test = new Date((long)1540027800*1000);
        Calendar a = Calendar.getInstance();
        a.setTime(test);
        a.setTimeInMillis(((long)1540027800*1000));

        //VOLUME DATASET
        Map<Date, Integer> volumeData = new HashMap<>();
        volumeData.put(new Date(1540027800), 400);
        volumeData.put(new Date(1540033200), 500);
        volumeData.put(new Date(1540045530), 450);
        volumeData.put(new Date(1540070024), 300);
        volumeData.put(new Date(1540057395), 400);

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

    private DailyReview getYesterdayTestData(){
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
        volumeData.put(new Date(1539940844), 300);
        volumeData.put(new Date(1539949223), 400);
        volumeData.put(new Date(1539955222), 300);
        volumeData.put(new Date(1539988252), 450);

        //WELLBEING DATASET
        Map<Date, Integer> wellbeingData = new HashMap<>();
        wellbeingData.put(new Date(1539940844), 0);
        wellbeingData.put(new Date(1539949223), 0);
        wellbeingData.put(new Date(1539955222), 0);
        wellbeingData.put(new Date(1539971162), 1);
        wellbeingData.put(new Date(1539988252), 1);


        tmp.calcStateGraph(stateData);
        tmp.calcStateChart(stateData);
        tmp.calcVolumeGraph(volumeData);
        tmp.calcBagGraph(volumeData);
        tmp.calcWellbeingChart(wellbeingData);

        return tmp;
    }
}
