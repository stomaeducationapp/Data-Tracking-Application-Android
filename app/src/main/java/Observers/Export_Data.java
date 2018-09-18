package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import Factory.Factory;

/**
 * <h1>Export_Data</h1>
 * The Export_Data Java Class is used to trigger code required to export the current users medical data
 * to an external database. This Observer has been created to reduce the coupling that would be required
 * between the Main Menu Package and Export Data Package.
 * Implements Time_Observer interface
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h>Changes</h1>
 * 27th Aug
 * Created Class Export_Data, Patrick Crockford
 * Added functionality to Notify Method, Patrick Crockford
 * Created null check Added Exception method signature, Patrick Crockford
 * <p>
 * 28th Aug
 * JavaDoc written up, Patrick Crockford
 * 17th Sept
 * Modified Code to comply with Requirements for Observer - Factory Integration, Patrick Crockford
 */
public class Export_Data implements Time_Observer {
    /**
     * Factory Object to construct classes from external packages
     */
    private Factory factory;

    /**
     * Instantiates a new Export_Data.
     */
    public Export_Data(Factory factory) {
        this.factory = factory;
    }

    /**
     * This method is used to construct and call the required class(es) to export users medical data to
     * and external database
     * @param input_Stream  Represents the FileInputStream Object used to read users data file stored on the device
     * @param output_Stream Represents the FileOutputStream Object used to write to the users medical data file stored on the device
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream) throws NullPointerException {
        if (input_Stream != null) {
            boolean valid = false;
            // TODO: 17-Sep-18 Uncomment and modify when export package has been created
            //Export_Handler export_handler = factory.Create_Export_Handler();
            //valid = export_handler.Export_Data(input_Stream, output_Stream);
            return valid;
        } else {
            if(input_Stream == null){
                throw new NullPointerException("Input Stream Object is Null");
            }else{
                throw new NullPointerException("Output Stream Object is Null");
            }
        }
    }
}
