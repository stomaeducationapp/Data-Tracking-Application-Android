package Observers;

import android.content.Context;
import android.content.Intent;

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
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(Map<Files, File> file_Map, Context context) throws NullPointerException {
        if (file_Map != null && !file_Map.isEmpty()) {
            boolean valid = false;
            ReviewHandler daily_review_calculator = factory.Make_Stoma_Review_Handler();
            valid = daily_review_calculator.generateReview();
            Intent data = daily_review_calculator.getViewIntent(context);
            //This currently breaks the rule to only use form_change to swap activities - but this is dude to the current setup of how review data is retrieved
            //If this is still present - a rework may be considered to pull review data from activity itself rather than extra classes
            context.startActivity(data); //TODO REWORK IF POSSIBLE INTO FORM_CHANGE
            return valid;
        } else {
            throw new NullPointerException("file_Map is Null or Empty");
        }
    }
}
