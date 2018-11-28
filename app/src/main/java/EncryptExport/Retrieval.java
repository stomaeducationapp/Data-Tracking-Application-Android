package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 28/11/2018
 */

/* CLASS/FILE DESCRIPTION
 * This file handles all file access functionality that the encrypt+export package needs
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 * 09/10/2018 - Brought up closer to final design
 * 16/10/2018 - Modified code to as close to final design as it known currently
 * 18/10/2018 - Updated to match new master pull
 * 27/11/2018 - Updated to prepare for integration of XML
 * 28/11/2018 - Added tags I missed
 */

/* REFERENCES
 * Creating list from predefined values learned from https://stackoverflow.com/questions/16194921/initializing-arraylist-with-some-predefined-values
 * XmlPullParser use learned from https://developer.android.com/reference/org/xmlpull/v1/XmlPullParser.html#constants_1 and https://developer.android.com/reference/org/xmlpull/v1/XmlPullParserFactory
 * And all related documentation on https://developer.android.com
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import Factory.Factory;
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
     * INPUTS - FileInputStream (user file location)
     * OUTPUTS - userFile (file with all data needed to be exported contained within)
     * PURPOSE - This is the function to call the OS to retrieve the user's data that must be exported
     */
    Map<String, String> retrieve(File input, Factory factory) throws EncryptHandlerException
    {

        Map<String, String> userFile = null; //The user's file we are going to try and retrieve
        Medical_Reader reader = (Medical_Reader) factory.Make_Reader(Factory.XML_Reader_Choice.Medical);
        ArrayList<XML_Reader.Tags_To_Read> tags = new ArrayList<XML_Reader.Tags_To_Read>(Arrays.asList(XML_Reader.Tags_To_Read.Export_Data, XML_Reader.Tags_To_Read.Bags, XML_Reader.Tags_To_Read.Urine, XML_Reader.Tags_To_Read.Wellbeing,
                XML_Reader.Tags_To_Read.Location, XML_Reader.Tags_To_Read.Entry_Time, XML_Reader.Tags_To_Read.Medical_State, XML_Reader.Tags_To_Read.Colour, XML_Reader.Tags_To_Read.Consistency, XML_Reader.Tags_To_Read.Physical,
                XML_Reader.Tags_To_Read.Volume, XML_Reader.Tags_To_Read.Hydration)); //Tags we want to read from the user's file


        try
        {
            userFile = reader.Read_File(input, tags, null); //Retrieve the data we need
            //TODO UPDATE TO USE ACCOUNT NAME IF FILE STORAGE METHOD CHANGES
        }
        catch ( XML_Reader_Exception | NullPointerException e)
        {
            throw new EncryptHandlerException("Failed to read in user data" + e.getMessage());
        }

        return userFile;
    }

    /* FUNCTION INFORMATION
     * NAME - bookKeeping
     * INPUTS - FileOutputStream (file with data we need to clean up)
     * OUTPUTS - boolean
     * PURPOSE - This is the function to go through stored user data and remove all entries that match entries within userFile,
     *           this both keeps memory low, and prevents any duplicate uses in the app (which could have massive ramifications with health tracking)
     */
    public boolean bookKeeping(File output, Map<String, String> userData, Factory factory) throws EncryptHandlerException
    {
        boolean success = false;
        Medical_Writer writer = (Medical_Writer) factory.Make_Writer(Factory.XML_Writer_Choice.Medical);

        try
        {
            success = writer.Write_File(output, userData, XML_Writer.Tags_To_Write.Export); //Tell writer to do in export mode (automatically cleans up for us)
        }
        catch (XML_Writer_Failure_Exception | XML_Writer_File_Layout_Exception e)
        {
            throw new EncryptHandlerException("Failed to write back to user file" + e.getMessage());
        }

        return success;
    }

}
