package XML;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * <h1>XML_Reader</h1>
 * The XML_Reader Interface Java Class is used to Allow access to the package private concrete classes that inherit from it.
 * It also stores the Enum Tags_To_Read required to specify what tags are to be read from the XMl file
 *
 * @author Patrick Crockford
 * @version 1.0
 * <h>Changes</h1>
 * 04th Sept
 * Created Class, Enum, Read_File method, and enum Tags_To_Read, Patrick Crockford
 * Javadoc, Patrick Crockford
 */
public interface XML_Reader {
    /**
     * The enum Tags to read from XML file. Covers all Concrete Classes that are broken up into sections
     */
    public enum Tags_To_Read {
        //Login Tags
        Account, Password,
        //Medical Tags
        Bags, Urine, Hydration, WellBeing, Location, Entry_Time,
        //Account Tags
        Gamification, Notification, State, Account_Name
    }

//Note need to Add Throws at a later Date
    /**
     * Read file map.
     *
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @param tags         the tags to read from the XML file specified
     * @return a Map with string pair values, with Tag name attached to the value read in, if empty it will be 'NaN' value
     */
    Map<String, String> Read_File(FileInputStream input_Stream, List<Tags_To_Read> tags) throws NullPointerException, XML_Reader_Exception;
}
