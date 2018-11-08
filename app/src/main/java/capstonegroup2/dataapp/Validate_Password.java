package capstonegroup2.dataapp;

import java.util.regex.Pattern;

import XML.Account_Reader;

/**
 * <h1>Validate_Password</h1>
 * Contains functions to sanitize and hash any input password.
 * @author Oliver Yeudall
 * @version 1.0
 */
public class Validate_Password implements Account_Reader {

    private String hashedPassword;
    private static final int minLength = 6; // Minimum allowable password length
    public enum retMessage{
        GOOD, BADLENGTH, BADCHAR, BADCODE, BADOTHER
    }

    /**
     * Default Constructor
     * DESCRIPTION: Sets all string field variables to empty strings
     */
    Validate_Password() {
        hashedPassword = "";
    }

    /**
     *
     * @return boolean representing a password of acceptable minimum length
     */
    private boolean validLength(String inPassword) {

        String testPassword = inPassword;
        boolean isValid = false;

        if (testPassword.length() != null) {
            if (testPassword.length() > minLength) {
                isValid = true;
            }
        }

        return isValid;
    }

    /**
     * @return boolean representing a password with the approved used character set
     */
    private boolean validCharacters(String inPassword) {

        String testPassword = inPassword;
        boolean isValid = false;

        if(Pattern.matches("[a-zA-Z]",testPassword)) {
            isValid = true;
        }



        return isValid;
    }

    /**
     * @return boolean representing a password without executable code
     */
    private boolean validNoCode(String inPassword) {

        String testPassword = inPassword;
        boolean isValid = true;

        return isValid;
    }

    /**
     * @return enum value returning the results of the password validation test
     */
    public retMessage isPasswordValid(String inPassword){

        String testPassword = inPassword;

        if (validLength(testPassword)) {
            if(validCharacters(testPassword)) {
                if(validNoCode(testPassword)) {
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
