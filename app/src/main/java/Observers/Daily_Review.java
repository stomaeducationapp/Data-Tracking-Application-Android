package Observers;

import java.io.FileInputStream;

/**
 * <h1>Daily_Review</h1>
 * The Update_Data_For_Account_From_File Java Class is used to Read In Account Information from XML file stored on the device.
 * This Observer has been created to reduce the coupling that would be required between the calling package and Operating system.
 * Implements Time_Observer interface
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Changes:</h1>
 * 27th Aug
 * Created 'Daily_Review', and added 'Notify' method, Patrick Crockford
 * Added Comment Block, Patrick Crockford
 * Added Method Exception Signatures, Patrick Crockford
 * <p>
 * 30th Aug
 * Changed to JavaDoc Commenting, Patrick Crockford
 * <h>NOTE</h>
 */
class Daily_Review implements Time_Observer {
    // TODO: 27-Aug-18 Remove comments around Factory when class has been created
    /**
     * Factory Object for creating Objects with dependency injection
     */
    //private Factory factory;

    /**
     * Instantiates a new Daily review.
     */
    public Daily_Review(/*Factory factory*/) {
        //this.factory = factory;
    }

    /**
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(FileInputStream input_Stream) throws NullPointerException{
        if (input_Stream != null) {
            boolean valid = false;
            // TODO: 27-Aug-18 When Daily Review Package is created uncomment below
        //Daily_Review_Calculator daily_review_calculator = factory.Create_Daily_Review_Calculator();
        //valid = daily_review_calculator.Generate_New_Review(input_Stream);
        return valid;
        } else{
            throw new NullPointerException("Input Stream Object is Null");
        }
    }
}
