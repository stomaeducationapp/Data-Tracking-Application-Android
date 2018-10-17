package Observers;

import java.io.File;
import java.util.Map;

/**
 * <h1>Time_Observer</h1>
 * The Time_Observer Interface Java Class is used to Allow access to the package private concrete classes that inherit
 * from it.
 * Daily_Review and Export_Data Java Classes inherit from this interface
 * <p>
 * The Map of File objects can be accessed through the public enum Files from this interface.
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h1>Last Edited</h1>
 * 17 Oct 2018
 * Patrick Crockford
 */
public interface Time_Observer {

    /**
     * Enums to be used as keys for storing the File objects in the Map to be passed to the classed called by the
     * concrete implementations
     */
    enum Files {
        Medical, Review, Account
    }

    /**
     * @param file_Map Map Object containing File Objects representing the type of file in relations to the Enum value
     *                 Key it is stored under, specific to the account currently logged in
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    boolean Notify(Map<Files, File> file_Map) throws NullPointerException;
}
