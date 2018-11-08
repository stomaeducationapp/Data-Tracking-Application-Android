package capstonegroup2.dataapp;

import java.util.regex.Pattern;

import XML.Account_Reader;

/**
 * <h1>Validate_Username</h1>
 * Contains functions to sanitize and evaluate usernames.
 * @author Oliver Yeudall
 * @version 1.0
 */
public class Validate_Username implements Account_Reader {

    private static final int minLength = 6; // Minimum allowable username length
    public enum retMessage{
        GOOD, BADLENGTH, BADCHAR, BADCODE, BADOTHER
    }

    /**
     * Default Constructor
     * DESCRIPTION: Sets all string field variables to empty strings
     */
    Validate_Username() {

    }


    /**
     *
     * @return boolean representing a username of acceptable minimum length
     */
    private boolean validLength(String inUsername) {

        String testUsername = inUsername;
        boolean isValid = false;

        if (testUsername.length() != null) {
            if (testUsername.length() > minLength) {
                isValid = true;
            }
        }

        return isValid;
    }

    /**
     * @return boolean representing a username with the approved used character set
     */
    private boolean validCharacters(String inUsername) {

        String testUsername = inUsername;
        boolean isValid = false;

        if(Pattern.matches("[a-zA-Z]",testUsername)) {
            isValid = true;
        }



        return isValid;
    }

    /**
     * @return boolean representing a username string without executable code
     */
    private boolean validNoCode(String inUsername) {

        String testUsername = inUsername;
        boolean isValid = true;

        return isValid;
    }

    /**
     * @return enum value returning the results of the username validation test
     */
    public retMessage isUsernameValid(String inUsername){

        String testUsername = inUsername;

        if (validLength(testUsername)) {
            if(validCharacters(testUsername)) {
                if(validNoCode(testUsername)) {
                    return retMessage.GOOD;
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
