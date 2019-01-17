package Observers;

import android.content.Context;

import java.io.File;
import java.text.Normalizer;
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
 * @version 1.3
 * <h1>Last Edited</h1>
 * 17 Jan 2019
 * Jeremy Dunnet
 */
public interface Time_Observer {

    /**
     * Enums to be used as keys for storing the File objects in the Map to be passed to the classed called by the
     * concrete implementations
     */
    enum Files {
        Medical, Review, Account, Login
    }

    /**
     * @param file_Map Map Object containing File Objects representing the type of file in relations to the Enum value
     *                 Key it is stored under, specific to the account currently logged in
     * @param context This is the context that refers to the calling class (in case a connected handler needs to reference this)
     * @param fc This is a reference to the Form_Change observer this observer may need to call to load an activity (depending on function)
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    boolean Notify(Map<Files, File> file_Map, Context context, Form_Change fc) throws NullPointerException;
}
