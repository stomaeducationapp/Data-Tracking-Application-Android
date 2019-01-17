package Observers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.File;
import java.util.Map;

import Factory.Factory;
import MedicalReview.ReviewHandler;

/**
 * <h1>Daily_Review</h1>
 * The Daily_Review Java Class is used to Read In Account Information from XML file stored on the device.
 * This Observer has been created to reduce the coupling that would be required between the calling package and
 * Operating system.
 * Implements Time_Observer interface
 *
 * @author Patrick Crockford
 * @version 1.1
 * <h1>Last Edited</h1>
 * 31 Dec 2018
 * Jeremy Dunnet
 */
public class Daily_Review implements Time_Observer {
    /**
     * Factory Object for creating Objects with dependency injection
     */
    private Factory factory;

    /**
     * Instantiates a new Daily review.
     */
    public Daily_Review(Factory factory) {
        this.factory = factory;
    }

    /**
     * @param file_Map Map Object containing File Objects representing the type of file in relations to the Enum value
     *                 Key it is stored under, specific to the account currently logged in
     * @param context The context of the application
     * @param fc The Form_Change observer reference so that the observer can change the activity to DailyReviewGraph
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(Map<Files, File> file_Map, Context context, Form_Change fc) throws NullPointerException {
        if (file_Map != null && !file_Map.isEmpty()) {
            boolean valid = false;
            ReviewHandler daily_review_calculator = factory.Make_Stoma_Review_Handler();
            valid = daily_review_calculator.generateReview();
            if(valid ==  true)
            {
                Bundle data = daily_review_calculator.getViewData();

                try {
                    fc.Change_Form_Bundle(Form_Change_Observer.Activity_Control.Review, context, data);
                }
                catch (Invalid_Enum_Exception e)
                {
                    throw new RuntimeException("Invalid Enum passed." + e.getMessage());
                }

            }

            return valid;
        } else {
            throw new NullPointerException("file_Map is Null or Empty");
        }
    }
}
