package XML;

import org.xmlpull.v1.XmlPullParser;

import java.util.List;
import java.util.Map;

/**
 * <h1>XML_Reader</h1>
 * The XML_Reader Interface Java Class is used to Allow access to the package private concrete classes that inherit from it.
 * It also stores the Enum Tags_To_Read required to specify what tags are to be read from the XMl file
 * <h>Note</h>
 * When Using the Medical_Reader Concrete Class the Map will contain String Pair Value under the key "Entries" as more than 1 maybe retrieved from file
 * This needs to be used as Keys corresponding to each Medical Entry will be appended with '-' + the number of the entry. E.G Medical Entry 2 Hydration will be 'Hydration-2"
 * This is to allow for easy string concatenation with syntax '-'
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
        Account_Name, Password,
        //Medical Tags
        Bags, Urine, Hydration, Wellbeing, Location, Entry_Time, Medical_State,
        //Account Tags
        Gamification, Notification, State, Name, Last_Daily_Review_Date,
        //Tags used for multiple entries retrieved, for use when getting last 24hours or all data to export. The number of entries will be recorded and returned the Map
        //Under the Key "Entries". This is for the Medical Data Only currently due to functionality.
        Last_Entry, Daily_Data, Export_Data,
        //Tag for number of entries returned with the medical reader
        Entries_Retrieved,
        
    }

//Note need to Add Throws at a later Date

    /**
     * Read file map.
     *
     * @param xmlPullParser Represents the XML Reader Object used to read users data file stored on the device
     * @param tags         the tags to read from the XML file specified
     * @param account_Name Name of the account for login purposes, Set to Null if not using Login_Reader Functionality
     * @return Map         Map with string pair values, with Tag name attached to the value read in, if empty it will be "".
     * @throws NullPointerException If the input_Stream Object is Null
     * @throws XML_Reader_Exception If an XmlPullParserException or IOException has occurred
     */
    
    Map<String, String> Read_File(XmlPullParser xmlPullParser, List<Tags_To_Read> tags, String account_Name) throws NullPointerException, XML_Reader_Exception;
}
