package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 09/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the interface design to allow multiple types of file export to be present within the app. In future implementations of this class simply convert the given map (desired object for encryption use) to a usable
 * object for your chosen export type (email, TCP direct to server, text message etc.) and use that method to send it.
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 * 09/10/2018 - Fixed with correct import parameters (of current design)
 */

/* REFERENCES
 *
 */

import java.util.Map;

public interface Export
{

    /* FUNCTION INFORMATION
     * NAME - exportFile
     * INPUTS - userFile (file to be exported)
     * OUTPUTS - none
     * PURPOSE - This is the function that will export the given file in whatever manner a developer decides they wish to send the file
     *           (email, SSL, text etc.) to the central repository to allow admin to monitor user's tracking. NOTE: As of this stage - this
     *           interface is not implemented at all in this application - it is a black box to be implemented when approved and method of
     *           delivery decided.
     */
    void exportFile(Map<String, String> serverFile);

}
