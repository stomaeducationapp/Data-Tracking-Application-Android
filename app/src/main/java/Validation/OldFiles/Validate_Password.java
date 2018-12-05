package Validation.OldFiles;

import java.util.regex.Pattern;

import Validation.Validation;

/**
 * <h1>Validate_Password</h1>
 * Contains functions to sanitize and assess input passwords against requirements.
 *
 * Purpose: Class aim is to check for correct parameters and sanitise input. Class is to be
 * instantiated and the general function isPasswordValid called. The calling program will pass
 * the value to be checked and the function will return an enum value. The potential enum return
 * values are:
 *     GOOD - The input argument is correct as per requirements
 *     BADLENGTH - The input argument does not match the required size
 *     BADCHAR - The input argument contains characters that are not approved, such as '!' or '<'
 *     BADCODE - There is an attempt at code injection in the input argument (feature in progress)
 *     BADOTHER - There is an unspecified problem with the input argument
 *
 * Progress: Mostly done, needs to have validNoCode() finished
 *
 * @author Oliver Yeudall
 * @version 1.0
 */
public class Validate_Password {

    private static final int minLength = 6; // Minimum allowable password length
    public enum retMessage{
        GOOD, BADLENGTH, BADCHAR, BADCODE, BADOTHER
    }

    /**
     * Default Constructor
     * DESCRIPTION: Sets all string field variables to empty strings
     */
    Validate_Password() {

    }

    /**
     *
     * @return boolean representing a password of acceptable minimum length
     */
    private boolean validLength(String inPassword) {

        String testPassword = inPassword;
        boolean isValid = false;

        if (testPassword.length() > minLength) {
            isValid = true;
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

        //In progress, needs finishing

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

        //return retMessage.BADOTHER;
    }
}
