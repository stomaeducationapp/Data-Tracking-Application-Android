package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 09/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the event handler for this package - it will trigger on a user/time elapsed event that requires the package
 * to get the most recent data and securely encrypt it for transport across back to the server it will be stored on
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 * 09/10/2018 - Changed to more like final design
 */

/* REFERENCES
 * And all related documentation on https://developer.android.com
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class Detector
{

    //Classfields
    private Object file; //The file that holds the user's data
    private Object enFile; //The final encrypted file

    //Constructors

    //Getters+Setters

    /* FUNCTION INFORMATION
     * NAME - handle
     * INPUTS - FileInputStream, FileOutputStream (tied to user files)
     * OUTPUTS - boolean
     * PURPOSE - This is the function to handle the encrypt/export event triggered by either the user manually or
     *           automatically after 7 days no export
     */
    public static boolean handle(FileInputStream input, FileOutputStream output) throws EncryptHandlerException
    {
        boolean done = false; //To tell calling method if we succeeded ot not

        //Package objects
        Retrieval sys = new Retrieval();
        Encrypt en = new Encrypt();
        //Export object to handle the exporting of data

        //Maps for user data
        Map<String, String> userFile;
        Map<String, String> enFile;

        try
        {
            //Call Retrieval to get the file/data back from system
            userFile = sys.retrieve(input);

            //Pass to Encrypt to convert file to encrypted
            enFile = en.encryptHandler(userFile);

            //Pass enFile to chosen export method

            //Clean up exported data
            boolean success = sys.bookKeeping(output);
            if(success == true) //If it was successful
            {
                done = true;
            }
        }
        catch ( NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
            throw new EncryptHandlerException("Encryption of file failed: " + e.getMessage());
        }

        return done;
    }



}
