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
    public static void handle()
    {
        //Call Retrieval to get the file/data back from system

        //Pass to Encrypt to convert file to encrypted

        //Pass encrypted to export

        //Call Retrieval? to remove all data from system file storage - to reduce memory and prevent duplicate records messing with other functions
    }



}
