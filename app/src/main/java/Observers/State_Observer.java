package Observers;

import java.io.File;

/**
 * <h1>State_Observer</h1>
 * The Time_Observer Interface Java Class is used to Allow access to the package private concrete classes that inherit
 * from it.
 * Check_State Java Class inherit from this interface
 *
 * @author Patrick Crockford
 * @version 1.1
 * <h1>Last Edited</h1>
 * 25th Feb 2019
 * Jeremy Dunnet
 */
public interface State_Observer {

    /**
     * @param medical Represents the File Object of the medical information file for the account currently logged in
     * @return True if state was successfully calculated and written to file, otherwise false
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    boolean Notify(File medical) throws NullPointerException;
}
