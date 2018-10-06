package EncryptExport;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 02/10/2018
 * LAST MODIFIED BY - Jeremy Dunnet 05/10/2018
 */

/* CLASS/FILE DESCRIPTION
 * This class is responsible for taking a user's retrieved data and encrypting it according to the AES standard of encryption
 * for transport across the net to be delivered to a server.
 */

/* VERSION HISTORY
 * 02/10/2018 - Created file and added comment design path for future coding
 * 05/10/2018 - Added a lot of base functionality according to research and loaded with comments for both understanding and future development use
 */

/* REFERENCES
 * The main basis for this code was adapted/learned from https://stackoverflow.com/questions/6788018/android-encryption-decryption-with-aes
 * The reasoning's behind uses of certain modes and Objects was learned from personal research summary, https://medium.com/@tiensinodev/basic-android-encryption-dos-and-don-ts-7bc2cd3335ff and https://proandroiddev.com/security-best-practices-symmetric-encryption-with-aes-in-java-7616beaaade9
 * Keystore use was learned from https://medium.com/@josiassena/using-the-android-keystore-system-to-store-sensitive-information-3a56175a454b
 * GCM padding learned from https://crypto.stackexchange.com/questions/42412/gcm-padding-or-not
 * Reason for using a charset in string byte encoding learned from http://www.java67.com/2017/10/3-ways-to-convert-string-to-byte-array-in-java.html
 * Reasoning for GCM learned from https://stackoverflow.com/questions/44425846/how-to-make-gcm-encrypt-with-authentication-tag-for-android
 * And all related documentation on https://developer.android.com
 */

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Encrypt
{

    public Encrypt() throws Exception //TODO - FIX WITH PROPER EXCEPTION HANDLING
    {

    }


    public static String decrypt(byte[] message, byte[] decKey, byte[] iv) throws Exception
    {

        //Another set of variables you can modify to alter how this method encrypts your data
        String algorithm = "AES"; //The variable for the algorithm type of the key you are going to use
        String cipherAlgorithm = "AES/GCM/NOPADDING"; //The variable for what type of encryption you are going to use
        //I have used AES (Current Australian Signals Directorate standard) with
        //GCM (Galois/Counter Mode - this was most commonly used and provides authentication as well as encryption - see reference below)
        //and a no padding option (since GCM uses a stream cipher style - no padding is needed)
        //DO NOT USE ECB (Electronic Codebook) as a mode (included as a restriction in ASD standard as do not use) - as this mode resuses the same key on each block of plaintext, meaning identical blocks will encrypt the same (VERY BAD!!!!)
        //https://proandroiddev.com/security-best-practices-symmetric-encryption-with-aes-in-java-7616beaaade9

        //SecretKeySpec is another interface to increase security
        SecretKeySpec skeySpec = new SecretKeySpec(decKey, algorithm);
        //Get a Cipher instance that will encrypt our data according to the algorithm we choose
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        final GCMParameterSpec spec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, spec);
        ///Do the decryption
        byte[] decrypted = cipher.doFinal(message);
        System.out.println("Decrypted file - " + decrypted); //TODO - REMOVE!!!
        String payload = new String(decrypted);

        return payload;
    }

    /* FUNCTION INFORMATION
     * NAME - encryptHandler
     * INPUTS - userFile (file to be encrypted)
     * OUTPUTS - encrypted (encrypted version of userFile)
     * PURPOSE - This is the function to begin the encryption process on the userFile provided
     */
    //TODO - REMOVE COMMENTS AND RE-ADD THE PROPER IMPORTS/RETURNS
    public static /*Object*/ String encryptHandler(/*Object userFile*/) throws Exception {

        //SAMPLE TEST STRING BEFORE USING FILE RETRIEVAL
        String testString = "Hello - I have secret info I want to send"; //TODO - DELETE
        System.out.println(testString);

        //These are variables you can use to alter how the encryption functions - I have initialised them here instead of hardcoding
        //them into the function calls so whatever encryption type/specifications you need can be easily altered here (also present in private function as well!)
        String algorithm = "AES"; //The variable for determining what encryption standard you wish to encrypt the data in
        String charSet = "UTF-8"; //The variable for What charset you will use to encode the string bytes as -  this is so you receive less hiccups in transfer across different systems
                                  //This was sourced from references above (http://www.java67.com/2017/10/3-ways-to-convert-string-to-byte-array-in-java.html and https://medium.com/@tiensinodev/basic-android-encryption-dos-and-don-ts-7bc2cd3335ff)
        int keySize = 128; //The variable for the size of the key you want

        //IMPORTANT - THIS CODE SHOULD BE IMPLEMENTED WHEN MOVING TO FULL SERVER BASED APP
        //This is the basic code outline for what you should be doing when encrypting using a server to handle communications between user phones and a central database
        //The database would be running a Certificate Authority (CA) that controls and distributes the encryption keys across the network
        /* Connect to server CA and request certification
         * This certificate will include what AES keys are valid for this transaction
         * Then the valid keys could be stored in the AndroidKeyStore (for extra security in using the keys) using the below KeyGenerator code (IF YOU USE API 23 or higher)
         * NOTE: You can move the server handshake (and KeyGenerator code) to any part of the app you want (since KeyStore is a OS level Object) - then just grab the generated keys in that section
         */

        // (!KEY - start) Store all keys generated by this KeyGenerator into the keystore
        final KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);

        //Transform user data from string to byte array (what most encryption methods use)
        byte[] b = testString.getBytes(charSet);

        //Create a cryptographic strength random number generator
        SecureRandom sr = new SecureRandom( /*genType*/ ); //genType is the chosen style of randomisation - my research has said that leaving the generation style to Android defaults is not major differnce
                                                           //But you may change to whatever type you want - DO NOT USE "SHA1PRNG" as articles online claim this is not as random as was thought!
        //Initialise the KeyGenerator to the size of the key we want and how to randomly generate data for it
        keyGenerator.init(keySize, sr);
        //Store the generated key in a SecretKey object (Android provides this interface to increase secure access to the key itself)
        SecretKey skey = keyGenerator.generateKey();
        System.out.println("Secret key is - " + keyGenerator.generateKey()); //TODO - REMOVE!!!
        //Get byte version of key (for use in encrypting)
        byte[] key = skey.getEncoded();
        System.out.println("Key is - " + key); //TODO - REMOVE!!!

        //Encrypt the data
        String finalResult = encrypt(key,b); //TODO - CHANGE BACK TO BYTE[] RETURN

        return finalResult;

    }

    //TODO - see if can do instanceof specific exceptions
    private static String encrypt(byte[] raw, byte[] clear) throws Exception
    {
        //Another set of variables you can modify to alter how this method encrypts your data
        String algorithm = "AES"; //The variable for the algorithm type of the key you are going to use
        String cipherAlgorithm = "AES/GCM/NOPADDING"; //The variable for what type of encryption you are going to use
                                                      //I have used AES (Current Australian Signals Directorate standard) with
                                                      //GCM (Galois/Counter Mode - this was most commonly used and provides authentication as well as encryption - see reference below)
                                                      //and a no padding option (since GCM uses a stream cipher style - no padding is needed)
                                                      //DO NOT USE ECB (Electronic Codebook) as a mode (included as a restriction in ASD standard as do not use) - as this mode resuses the same key on each block of plaintext, meaning identical blocks will encrypt the same (VERY BAD!!!!)
                                                      //https://proandroiddev.com/security-best-practices-symmetric-encryption-with-aes-in-java-7616beaaade9

        //SecretKeySpec is another interface to increase security
        SecretKeySpec skeySpec = new SecretKeySpec(raw, algorithm);
        //Get a Cipher instance that will encrypt our data according to the algorithm we choose
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        //Initialize the cipher with our key and tell it we only want it to encrypt or data right now
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        //Grab an initialization Vector - mainly for ability to decrypt later (SAVE THIS - could be in KeyStore - up to you)
        byte[] iv = cipher.getIV();
        ///Do the encryption
        byte[] encrypted = cipher.doFinal(clear);
        System.out.println("Encrypted is - " + encrypted); //TODO - REMOVE!!!
        //There is some advice online stating that converting the encrypted data to a String/Base64 encoded version will help with transport across systems (due to higher support for those charsets)
        //While this may be true - using GCM/CCH tends to fail with this approach, this is because the encryption style includes and authentication tag (useful for protection against modification/certification)
        //When you encrypt the Cipher object includes the tag at the end of the ciphertext returned - so if you encode then decode at receiver this tag may change and cause a failure
        //You could get around this (especially if using a storage method for key/iv transport) by clipping out the tag from the ciphertext (the tag size is usually the key size) then sending that along with the encoded ciphertext (String/Base64)
        //BASE64/String reasoning = https://medium.com/@tiensinodev/basic-android-encryption-dos-and-don-ts-7bc2cd3335ff
        //GCM reasoning = https://stackoverflow.com/questions/44425846/how-to-make-gcm-encrypt-with-authentication-tag-for-android

        //TODO - REMOVE!!!
        String result = decrypt(encrypted, raw, iv);

        return result;
    }

}
