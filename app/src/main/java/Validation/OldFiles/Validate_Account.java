package Validation.OldFiles;

import java.util.regex.Pattern;

import Validation.Validation;

/**
 * <h1>Validate_Account</h1>
 * Contains functions to sanitize and ensure account information is in the correct format.
 *
 * Purpose: Class aim is to check for correct parameters and sanitise input. Class is to be
 * instantiated and the general function isAccountNameValid called. The calling program will pass
 * the value to be checked and the function will return an enum value. The potential enum return
 * values are:
 *     GOOD - The input argument is correct as per requirements
 *     BADLENGTH - The input argument does not match the required size
 *     BADCHAR - The input argument contains characters that are not approved, such as '!' or '<'
 *     BADCODE - There is an attempt at code injection in the input argument (feature in progress)
 *     BADOTHER - There is an unspecified problem with the input argument
 *     BADACCOUNT - There is an error with the specific account, such as the account not existing
 *
 * Progress: Specific implementation of input validation for BADCODE and BADACCOUNT
 *
 * @author Oliver Yeudall
 * @version 1.0
 */
public class Validate_Account {

    private static final int minLength = 6; // Minimum allowable account name length
    public enum retMessage{
        GOOD, BADLENGTH, BADCHAR, BADCODE, BADOTHER, BADACCOUNT
    }

    /**
     * Default Constructor
     * DESCRIPTION: Sets all string field variables to empty strings
     */
    Validate_Account() {

    }

    /**
     *
     * @return boolean indicating if the account name is already in use
     */
    private boolean accountAvailable(String inAccountName) {

        //Function in progress

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

         if (testAccountName.length() > minLength) {
             isValid = true;
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

        //Function in progress

        String testAccountName = inAccountName;
        boolean isValid = true;

        return isValid;
    }

    /**
     * @return enum value returning the results of the account validation test
     */
    public retMessage isAccountNameValid(String inAccountName){

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

        //return retMessage.BADOTHER;
    }
}