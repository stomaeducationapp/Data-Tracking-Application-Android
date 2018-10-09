package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 02/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This file handles all file access functionality that the encrypt+export package needs
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * X
 */

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import XML.Medical_Reader;
import XML.Medical_Writer;
import XML.XML_Reader;
import XML.XML_Reader_Exception;
import XML.XML_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;

public class Retrieval
{

    /* FUNCTION INFORMATION
     * NAME - retrieve
     * INPUTS - none (maybe user info?)
     * OUTPUTS - userFile (file with all data needed to be exported contained within)
     * PURPOSE - This is the function to call the OS to retrieve the user's data that must be exported
     */
    public Map<String, String> retrieve(FileInputStream input) throws EncryptHandlerException
    {

        Map<String, String> userFile = null; //The user's file we are going to try and retrieve
        XmlPullParser userParser = null;
        Medical_Reader reader = new Medical_Reader();
        List<XML_Reader.Tags_To_Read> tags = Arrays.asList(XML_Reader.Tags_To_Read.Bags, XML_Reader.Tags_To_Read.Urine, XML_Reader.Tags_To_Read.WellBeing,
                XML_Reader.Tags_To_Read.Location, XML_Reader.Tags_To_Read.Entry_Time, XML_Reader.Tags_To_Read.Medical_State);

        try
        {
            userParser = (XmlPullParserFactory.newInstance()).newPullParser(); //TODO - FIND OUT ABOUT ENCODING + IF NEED TO SET NAMESPACE AWARE!
            userParser.setInput(input, null);
            userFile = reader.Read_File(userParser, tags, null);
        }
        catch (XmlPullParserException | XML_Reader_Exception | NullPointerException e)
        {
            throw new EncryptHandlerException("Failed to read in user data" + e.getMessage());
        }

        return userFile;
    }

    /* FUNCTION INFORMATION
     * NAME - bookKeeping
     * INPUTS - userFile (file with data we need to clean up
     * OUTPUTS - none
     * PURPOSE - This is the function to go through stored user data and remove all entries that match entries within userFile,
     *           this both keeps memory low, and prevents any duplicate uses in the app (which could have massive ramifications with health tracking)
     */
    public boolean bookKeeping(FileOutputStream output) throws EncryptHandlerException
    {
        boolean success = false;
        Medical_Writer writer = new Medical_Writer();
        File userFile = null; //TODO - FIGURE OUT HOW TO CONVERT FILEOUTPUTSTREAM TO A FILE

        try
        {
            success = writer.Write_File(userFile, null,XML_Writer.Tags_To_Write.Export);
        }
        catch (XML_Writer_Failure_Exception | XML_Writer_File_Layout_Exception e)
        {
            throw new EncryptHandlerException("Failed to write back to user file" + e.getMessage());
        }

        return success;
    }

}
