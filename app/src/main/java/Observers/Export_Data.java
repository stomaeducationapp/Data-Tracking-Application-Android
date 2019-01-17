package Observers;

import android.content.Context;

import java.io.File;
import java.util.Map;

import EncryptExport.Detector;
import EncryptExport.Encrypt;
import EncryptExport.EncryptHandlerException;
import Factory.Factory;

/**
 * <h1>Export_Data</h1>
 * The Export_Data Java Class is used to trigger code required to export the current users medical data
 * to an external database. This Observer has been created to reduce the coupling that would be required
 * between the Main Menu Package and Export Data Package.
 * Implements Time_Observer interface
 *
 * @author Patrick Crockford
 * @version 1.2
 * <h1>Last Edited</h1>
 * 17th Jan 2019
 * Updated to fit new Time_Observer template - Jeremy Dunnet
 */
public class Export_Data implements Time_Observer {
    /**
     * Factory Object to construct classes from external packages
     */
    private Factory factory;

    //TODO REMOVE WHEN FINISHED INTEGRATION TESTING
    Detector testD;

    /**
     * Instantiates a new Export_Data.
     */
    public Export_Data(Factory factory) {
        this.factory = factory;
    }

    /**
     * @param file_Map Map Object containing File Objects representing the type of file in relations to the Enum value
     *                 Key it is stored under, specific to the account currently logged in
     * @param context Context of the application
     * @param fc Form_Change observer reference
     * @return True if daily 24 hour review is successfully calculated and saved to file, otherwise false.
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(Map<Files, File> file_Map, Context context, Form_Change fc) throws NullPointerException {
        if (file_Map != null && !file_Map.isEmpty()) {
            boolean valid = false;

            Detector d = factory.makeDetector();

            //TODO REMOVE WHEN FINISHED INTEGRATION TESTING
            testD = d;

            try {
                valid = d.handle(file_Map.get(Files.Medical), file_Map.get(Files.Medical), factory);
            }
            catch (EncryptHandlerException e)
            {
                throw new RuntimeException("SOMETHING WENT HORRIBLY WRONG"); //TODO UPDATE WITH A BETTER EXCEPTION WHEN MAIN IMPLEMENTED
            }

            return valid;
        } else {
            throw new NullPointerException("file_Map is Null or Empty");
        }
    }

    public String dTest()
    {
        String m = "";
        try {
            m = testD.test();
        }
        catch(EncryptHandlerException e)
        {
            ;
        }

        return m;
    }
}
