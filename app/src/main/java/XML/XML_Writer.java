package XML;

import java.io.File;
import java.util.Map;

/**
 * <h1>XML_Writer</h1>
 * The XML_Writer Interface Java Class is used to Allow access to the package
 * private concrete classes that inherit from it. It also stores the Enum
 * Tags_To_Write required to specify what tags are to be written to the XML file
 * <h>Note</h>
 * Not all control statements are valid for all concrete packages, some will cause errors to be thrown as they are not
 * supported due to available functionality
 *
 * @author Patrick Crockford
 * @version 1.1
 * <h1>Last Edited</h1>
 * 4-Nov-2018
 * Jeremy Dunnet
 */
public interface XML_Writer {

    /**
     * The enum Tags to write from XML file. Covers all Concrete Classes that are broken up into sections
     * <p>
     * <h1>Task Statements</h1>
     * Modify: Will modify fields provided in the values Map, usable in all concrete classes
     * New: Will create a new entry with the values provided in the values Map, not usable for Account_Writer concrete class
     * Export: Will delete all entries except those required for the current 24 hour review to be generated, only usable in the Medical_Writer concrete class
     * Create: Will initialise the .xml file with the correct root tags and other secondary tags as specified in each concrete class, usable in all concrete classes
     * Delete: Will delete the corresponding entry from the .xml file, only usable in the Login_Writer concrete class
     */
    enum Tags_To_Write {
        Account_Name, Password, New_Account_Name, //Login
        Name, Gamification, Notification, State, Export_Settings, Last_Daily_Review_Date, Last_Export_Date, Security_Question_ID, Security_Answer,//Account
        Bags, Urine, Colour, Volume, Physical, Consistency, Hydration, WellBeing, Location, Entry_Time, Medical_State, //Medical
        Modify, New, Export, Create, Delete//Task Statements
    }

    /**
     * Public Method Call to writer information specified to the file references by the File object and returns Boolean
     * if successful or not.
     *
     * @param file   Represents the File Object that references the .xml file to write and or modify information,
     *               to.
     * @param values The Map containing String pair values, with the key representing the field to write to and
     *               the value what will be written.
     * @param task   Defines what task should be carried out on the File
     * @return True if the writer is successful otherwise false
     * @throws XML.XML_Writer_File_Layout_Exception if the XML document given by the account_File object doesn't contain
     *                                              the expected XML layout
     * @throws XML.XML_Writer_Failure_Exception     if writing to the XML document encounters an unrecoverable error.
     */
    Boolean Write_File(File file, Map<String, String> values, Tags_To_Write task) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception;
}
