package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <h1>State_Observer</h1>
 * The Time_Observer Interface Java Class is used to Allow access to the package private concrete classes that inherit from it.
 * Check_State Java Class inherit from this interface
 *
 * @author Patrick Crockford
 * @version 1.0 <h>Changes</h1> 27th Aug Created State_Observer interface and Notfiy Method, Patrick Crockford <p> 29th Aug JavaDoc written up, Patrick Crockford
 */
public interface State_Observer {

    /**
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @param output_Stream Represents the FileOutputStream Object used to write to the users medical data stored on the device
     * @return True if state was successfully calculated and written to file, otherwise false
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream)throws NullPointerException;
}
