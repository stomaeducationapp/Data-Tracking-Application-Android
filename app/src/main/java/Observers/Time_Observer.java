package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * <h1>Time_Observer</h1>
 * The Time_Observer Interface Java Class is used to Allow access to the package private concrete classes that inherit from it.
 * Daily_Review and Export_Data Java Classes inherit from this interface
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h>Changes</h1>
 * 27th Aug
 * Created Interface 'Time_Observer', and Notify Method, Patrick Crockford
 * <p>
 * 30th Aug
 * JavaDoc commenting, Patrick Crockford
 */
public interface Time_Observer {

    /**
     * @param input_Stream  Represents the FileInputStream Object used to read users data file stored on the device
     * @param output_Stream Represents the FileOutputStream Object used to write to the users medical data file stored on the device
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream) throws NullPointerException;
}
