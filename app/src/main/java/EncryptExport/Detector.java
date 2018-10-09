package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 02/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the event handler for this package - it will trigger on a user/time elapsed event that requires the package
 * to get the most recent data and securely encrypt it for transport across back to the server it will be stored on
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * X
 */

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - This is the function to handle the encrypt/export event triggered by either the user manually or
     *           automatically after 7 days no export
     */
    public static boolean handle(FileInputStream input, FileOutputStream output) throws EncryptHandlerException
    {
        boolean done = false;
        Retrieval sys = new Retrieval();
        Encrypt en = new Encrypt();
        //Export object to handle the exporting of data
        FileDescriptor userFD;
        FileDescriptor serverFD;


        try
        {
            //Call Retrieval to get the file/data back from system
            try
            {
                userFD = input.getFD();
                serverFD = output.getFD();
            }
            catch (IOException e)
            {
                throw new EncryptHandlerException("Failed to retrieve file descriptors.");
            }
            Map<String, String> userFile;
            Map<String, String> enFile;

            userFile = sys.retrieve(userFD);

            //Pass to Encrypt to convert file to encrypted
            try
            {
                enFile = en.encryptHandler(userFile);
            }
            catch ()
            {

            }

            //TODO - try catch encryptHandle to capture encryption specific exceptions

            //Pass encrypted to export

            //Call Retrieval? to remove all data from system file storage - to reduce memory and prevent duplicate records messing with other functions
            sucess = sys.bookKeeping(userFile);
            done = true;
        }
        catch ()
        {

        }

        return done;
    }



}
