package medical_review_test;

import android.content.Context;
import android.content.Intent;

import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import MedicalReview.DailyReview;
import MedicalReview.ReviewData;
import MedicalReview.ReviewHandler;

/**
 * Testing the support classes and methods of the MedicalReview package
 * @author Ethan
 */
public class Review_Handler_Test {

    /**
     * This method tests if default state of the object can be created
     */
    @Test
    public void Test_Object_Creation() {
        ReviewHandler handler = new ReviewHandler();
        assertNotNull(handler);
    }

    /**
     * Check whether the parseData method can successfully retrieve the correct entries from the
     * full dataset
     */
    @Test
    public void Test_Parse_Data() {
        ReviewHandler handler = new ReviewHandler();
        //k=attribute, v=time,value
        Map<String, String> mockData = new HashMap<>();

        //instantiate test data set
        mockData.put("state1", "1540027800,3");
        mockData.put("output1", "1540027800,300");
        mockData.put("wellbeing1", "1540027800,good");
        mockData.put("state2", "1540045530,5");
        mockData.put("output2", "1540045530,300");
        mockData.put("wellbeing2", "1540045530,good");
        mockData.put("state3", "1540057395,5");
        mockData.put("output3", "1540057395,400");
        mockData.put("wellbeing3", "1540057395,bad");

        //check that each attribute can be parsed correctly - should take 3 of the 9 options
        //STATE
        Map<Date, Integer> test = handler.parseData(mockData, ReviewHandler.TYPE.STATELINE);
        assertEquals(test.size(), 3);

        //VOLUME
        test = handler.parseData(mockData, ReviewHandler.TYPE.VOLUMELINE);
        assertEquals(test.size(), 3);

        //WELL BEING
        test = handler.parseData(mockData, ReviewHandler.TYPE.WELLBEING);
        assertEquals(test.size(), 3);
    }

    /**
     * Verify whether the ReviewHandler class can successfully create the DailyReview objects
     */
    @Test
    public void Test_New_Reviews() {
        ReviewHandler handler = new ReviewHandler();
        //k=attribute, v=time,value
        Map<String, String> mockData = new HashMap<>();

        //instantiate test data set
        mockData.put("state1", "1540027800,3");
        mockData.put("output1", "1540027800,300");
        mockData.put("wellbeing1", "1540027800,good");
        mockData.put("state2", "1540045530,5");
        mockData.put("output2", "1540045530,300");
        mockData.put("wellbeing2", "1540045530,good");
        mockData.put("state3", "1540057395,5");
        mockData.put("output3", "1540057395,400");
        mockData.put("wellbeing3", "1540057395,bad");

        //check if newReview method succeeds
        assertTrue(handler.newReview(mockData));
    }
}