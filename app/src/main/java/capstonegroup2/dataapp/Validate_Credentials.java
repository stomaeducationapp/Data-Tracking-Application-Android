package capstonegroup2.dataapp;

import java.util.regex.Pattern;

import XML.Account_Reader;

/**
 * <h1>Validate_Credentials</h1>
 * Contains functions to sanitize and ensure user credentials have been entered in the correct format.
 * @author Oliver Yeudall
 * @version 1.0
 */
public class Validate_Credentials implements Account_Reader {

    private static final int minLength = 1; // Minimum allowable account name length
    public enum retMessage{
        GOOD, BADLENGTH, BADCHAR, BADCODE, BADOTHER, BADACCOUNT
    }

    /**
     * Default Constructor
     * DESCRIPTION: Sets all string field variables to empty strings
     */
    Validate_Credentials() {

    }

    /**
     *
     * @return boolean which indicates if an account really exists
     */
    private boolean accountAvailable(String inAccountName) {

        String testAccountName = inAccountName;
        boolean isValid = true;

        // If retrieved account is null, then the account name is unique


        return isValid;
    }

    /**
     *
     * @return boolean representing an account name of acceptable minimum length
     */
    private boolean validLength(String inAccountName) {

        String testAccountName = inAccountName;
        boolean isValid = false;

        if (testAccountName.length() != null) {
            if (testAccountName.length() > minLength) {
                isValid = true;
            }
        }

        return isValid;
    }

    /**
     * @return boolean representing an account name with the approved used character set
     */
    private boolean validCharacters(String inAccountName) {

        String testAccountName = inAccountName;
        boolean isValid = false;

        if(Pattern.matches("[a-zA-Z]",testAccountName)) {
            isValid = true;
        }



        return isValid;
    }

    /**
     * @return boolean representing an account name without executable code
     */
    private boolean validNoCode(String inAccountName) {

        String testAccountName = inAccountName;
        boolean isValid = true;

        return isValid;
    }

    /**
     * @return enum value returning the results of the account credentials validation test
     */
    public retMessage isCredentialsValid(String inAccountName){

        String testAccountName = inAccountName;

        if (validLength(testAccountName)) {
            if(validCharacters(testAccountName)) {
                if(validNoCode(testAccountName)) {
                    if(accountAvailable(testAccountName)) {
                        return retMessage.GOOD;
                    }
                    else {
                        return retMessage.BADACCOUNT;
                    }
                }
                else {
                    return retMessage.BADCODE;
                }
            }
            else {
                return retMessage.BADCHAR;
            }
        }
        else
        {
            return retMessage.BADLENGTH;
        }

        return retMessage.BADOTHER;
    }
}