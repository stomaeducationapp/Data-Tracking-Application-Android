package XML;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * <h1>XML_Writer</h1>
 * The XML_Writer Interface Java Class is used to Allow access to the package private concrete classes that inherit from
 * it.
 * It also stores the Enum Tags_To_Write required to specify what tags are to be written to the XML file
 * <h>Note</h>
 * When Using the Medical Writer, The Enum value "Number_Of_Entries_To_Write" needs to be given, even when only 1. The
 * entries need to be appended with the number value.
 * e.g 2nd entry will have all keys appended with '2'. This allows differentiation between them. The order of entries
 * will be X -> 1 in the XML file, where X is the highest entry.
 *
 * @author Patrick Crockford
 * @author Patrick Crockford
 * @version 1.0
 * <h>Changes</h1>
 * 12th Sept
 * Created Class, Write_File method, and enum Tags_To_Write, Patrick Crockford
 * Javadoc, Patrick Crockford
 */
public interface XML_Writer {
    /**
     *
     */
    public enum Tags_To_Write {
        
        Account_Name, Password, //Login
        Name, Gamification, Notification, State, Last_Daily_Review_Date,//Account
        Bags, Urine, Hydration, WellBeing, Location, Entry_Time, Medical_State, //Medical
        Number_Of_Entries_To_Write, //Control Statements
        Modify, New, Export, Create

    }

    /**
     * @param account_File Represents the File Object used to write users data to the specified file stored on the
     *                     device
     * @param values       Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @return True if successful otherwise false
     */
    Boolean Write_File(File account_File, Map<String, String> values, Tags_To_Write task) throws IOException, ParserConfigurationException, SAXException, TransformerException, XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception;


}

