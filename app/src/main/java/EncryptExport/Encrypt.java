package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 02/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This class is responsible for taking a user's retrieved data and encrypting it according to the AES standard of encryption
 * for transport across the net to be delivered to a server.
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 */

/* REFERENCES
 * X
 */

class Encrypt
{

    /* FUNCTION INFORMATION
     * NAME - encrypt
     * INPUTS - userFile (file to be encrypted)
     * OUTPUTS - encrypted (encrypted version of userFile)
     * PURPOSE - This is the function to encrypt the data within userFile according to the AES encryption scheme to protect/secure the data
     *           during transit
     */
    public static Object encrypt(Object userFile)
    {

        Object encrypted; //Final encrypted version of userFile
        String key; //AES key we will be using

        //Retrieve AES key from server (Signed certificate - enables both server and app to decrypt files sharing the certificate)

        //Use android AES library to enrypt file

        return encrypted;

    }

}
