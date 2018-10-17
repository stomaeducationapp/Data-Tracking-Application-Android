package Observers;

import java.io.File;
import java.util.Map;

import Factory.Factory;

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
            // TODO: 17-Sep-18 Uncomment and modify when Daily Review package has been created
            //Daily_Review_Calculator daily_review_calculator = factory.Create_Daily_Review_Calculator();
            //valid = daily_review_calculator.Generate_New_Review(input_Stream, output_Stream);
            return valid;
        } else {
            throw new NullPointerException("file_Map is Null or Empty");
        }
    }
}
