package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import Factory.Factory;

/**
 * <h>Check_State</h>
 * The Check_State Java Class is used to trigger code required to calculate the current medical 'state'
 * the current user is in. This Observer has been created to reduce the coupling that would be required
 * between the Medical Data Input Package and Medical States Package.
 * Implements State_Observer
 *
 * @author Patrick Crockford
 * @version 1.0 <h>Changes</h1>
 * 27th Aug Created Class Check_State, Patrick Crockford
 * Added functionality to Notify Method, Patrick Crockford
 * Created null check Added Exception method signature, Patrick Crockford
 * <p>
 * 29th Aug
 * JavaDoc written up, Patrick Crockford
 * 17th Sept
 * Modified Code to comply with Requirements for Observer - Factory Integration, Patrick Crockford
 */
public class Check_State implements State_Observer {


    private Factory factory;

    /**
     * Instantiates a new Check_State.
     */
    public Check_State(Factory factory) {
        this.factory = factory;
    }

    /**
     * @param input_Stream  Represents the FileInputStream Object used to read users data file stored on the device
     * @param output_Stream Represents the FileOutputStream Object used to write users medical data to the correct file stored on the device
     * @return True if state was successfully calculated and written to file, otherwise false
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream) throws NullPointerException {
        if (input_Stream != null && output_Stream != null) {
            boolean valid = false;
            // TODO: 17-Sep-18 Uncomment and modify when State Calculator package has been created
            //Stoma_State_Calculator stoma_state_calculator = Factory.Create_Stoma_State_Calculator();
            //stoma_state_calculator.Calculate_State();
            return valid;
        } else {
            if (input_Stream == null) {
                throw new NullPointerException("Input Stream Object is Null");
            } else {
                throw new NullPointerException("Output Stream Object is Null");
            }
        }
    }
}
