package Observers;

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
 * @version 1.0
 * <h1>Last Edited</h1>
 * 17 Oct 2018
 * Patrick Crockford
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
    public boolean Notify(Map<Files, File> file_Map) throws NullPointerException {
        if (file_Map != null && !file_Map.isEmpty()) {
            boolean valid = false;
            ReviewHandler daily_review_calculator = factory.Make_Stoma_Review_Handler();
            //TODO: Uncomment input/output stream when finalised
            valid = daily_review_calculator.generateReview(/*input_Stream, output_Stream*/);
            return valid;
        } else {
            throw new NullPointerException("file_Map is Null or Empty");
        }
    }
}
