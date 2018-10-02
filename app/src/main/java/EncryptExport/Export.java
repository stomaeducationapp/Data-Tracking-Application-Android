package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 02/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * X
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * X
 */

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
    void exportFile(Object userFile);

}
