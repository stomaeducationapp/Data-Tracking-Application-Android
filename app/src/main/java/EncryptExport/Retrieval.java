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

class Retrieval
{

    /* FUNCTION INFORMATION
     * NAME - retrieve
     * INPUTS - none (maybe user info?)
     * OUTPUTS - userFile (file with all data needed to be exported contained within)
     * PURPOSE - This is the function to call the OS to retrieve the user's data that must be exported
     */
    public static Object retrieve()
    {
        Object userFile; //The user's file we are going to try and retrieve

        //Get user's data from system storage based on known user info
        //Retrieve all data currently stored (We export in batches)

        return userFile;
    }

    /* FUNCTION INFORMATION
     * NAME - bookKeeping
     * INPUTS - userFile (file with data we need to clean up
     * OUTPUTS - none
     * PURPOSE - This is the function to go through stored user data and remove all entries that match entries within userFile,
     *           this both keeps memory low, and prevents any duplicate uses in the app (which could have massive ramifications with health tracking)
     */
    public static void bookKeeping(Object userFile)
    {

        //Delete every record that matches the date times in the provided file - in case other unsent data was added to file
        //while export occurred

    }

}
