package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <h>Check_State</h>
 * The Check_State Java Class is used to trigger code required to calculate the current medical 'state'
 * the current user is in. This Observer has been created to reduce the coupling that would be required
 * between the Medical Data Input Package and Medical States Package.
 * Implements State_Observer
 *
 * @author Patrick Crockford
 * @version 1.0 <h>Changes</h1> 27th Aug Created Class Check_State, Patrick Crockford Added functionality to Notify Method, Patrick Crockford Created null check Added Exception method signature, Patrick Crockford <p> 29th Aug JavaDoc written up, Patrick Crockford
 */
class Check_State implements State_Observer {

    // TODO: 27-Aug-18 WHen Factory Class created uncomment 
//private Factory factory;

    /**
     * Instantiates a new Check_State.
     */
    public Check_State(/*Factory factory*/) {
        //this.factory = factory;
    }

    /**
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @param output_Stream Represents the FileOutputStream Object used to write users medical data to the correct file stored on the device
     * @return True is state was successfully calculated and written to file, otherwise false
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream) throws NullPointerException{
        if (input_Stream != null && output_Stream != null) {
        boolean valid = false;
            // TODO: 27-Aug-18 Uncomment when state Calculator package is created with classes, May need to catch more exceptions depending on how the other package is created
        //Stoma_State_Calculator stoma_state_calculator = Factory.Create_Stoma_State_Calculator();
        //stoma_state_calculator.Calculate_State();
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
