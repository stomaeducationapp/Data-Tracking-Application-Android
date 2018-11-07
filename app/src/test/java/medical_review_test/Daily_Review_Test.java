package medical_review_test;

import android.content.Context;

import org.achartengine.GraphicalView;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import MedicalReview.DailyReview;

public class Daily_Review_Test {

    @Test
    public void Test_Constructors() {
        DailyReview review = new DailyReview();
        //Test default constructor
        assertNotNull(review);

            //SETTING THE SERIES FOR THE OBJECT
            Map<Date, Integer> mockData;

            //Set values to instance review
            mockData = setMockStateData();
            review.calcStateGraph(mockData);
            review.calcStateChart(mockData);
            mockData = setMockOutputData();
            review.calcVolumeGraph(mockData);
            review.calcBagGraph(mockData);
            mockData = setMockWellbeingData();
            review.calcWellbeingChart(mockData);

        DailyReview newReview = new DailyReview(review);
        //test copy constructor
        assertNotNull(newReview);
    }

    @Test
    public void Test_Calc_Methods() {
        DailyReview review = new DailyReview();
        //SETTING THE SERIES FOR THE OBJECT
        Map<Date, Integer> mockData;

        //Set to state values
        mockData = setMockStateData();
        //verify graphs can be calculated
        assertTrue(review.calcStateGraph(mockData));
        assertTrue(review.calcStateChart(mockData));

        //re-instance with output values
        mockData = setMockOutputData();

        assertTrue(review.calcVolumeGraph(mockData));
        assertTrue(review.calcBagGraph(mockData));

        //re-instance with wellbeing values
        mockData = setMockWellbeingData();

        assertTrue(review.calcWellbeingChart(mockData));
    }

    @Test
    public void Test_Display_Methods() {
        DailyReview review = new DailyReview();

        //SETTING THE SERIES FOR THE OBJECT
        Map<Date, Integer> mockData;
        //Set values to instance review
        mockData = setMockStateData();
        review.calcStateGraph(mockData);
        review.calcStateChart(mockData);
        mockData = setMockOutputData();
        review.calcVolumeGraph(mockData);
        review.calcBagGraph(mockData);
        mockData = setMockWellbeingData();
        review.calcWellbeingChart(mockData);

        Context context = mock(Context.class);

        //ACTUAL STUFF TO BE TESTED
        //NOTE: Chart engine doesn't work without running activity
        /*assertNotNull(review.displayStateGraph(context));
        assertNotNull(review.displayStateChart(context));
        assertNotNull(review.displayVolumeGraph(context));
        assertNotNull(review.displayBagGraph(context));
        assertNotNull(review.displayWellbeingChart(context));*/
    }

    private Map<Date, Integer> setMockStateData() {
        //k=attribute, v=time,value
        Map<Date, Integer> mockData = new HashMap<>();
        //instantiate test data set to state values
        mockData.put( new Date(1540027800),3);
        mockData.put(new Date(1540045530),5);
        mockData.put(new Date(1540057395),5);

        return mockData;
    }

    private Map<Date, Integer> setMockOutputData() {
        //k=attribute, v=time,value
        Map<Date, Integer> mockData = new HashMap<>();
        //instantiate test data set to state values
        mockData.put( new Date(1540027800),300);
        mockData.put(new Date(1540045530),400);
        mockData.put(new Date(1540057395),500);

        return mockData;
    }

    private Map<Date, Integer> setMockWellbeingData() {
        //k=attribute, v=time,value
        Map<Date, Integer> mockData = new HashMap<>();
        //instantiate test data set to state values
        mockData.put( new Date(1540027800),0);
        mockData.put(new Date(1540045530),1);
        mockData.put(new Date(1540057395),1);

        return mockData;
    }
}
