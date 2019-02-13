package Validation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 4/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 13/02/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the class that handles input validation (process of checking user input for non-allowed characters)
 * to ensure any input that is stored/passed to important classes is not malicious in nature.
 */

/* VERSION HISTORY
 * 4/12/2018 - Created basic code setup from previous iteration by Oliver Yeudall (Combined since splitting into separate classes seemed redundant)
 * 5/12/2018 - Implemented functionality and unit tested
 * 13/02/2018 - Implemented Serializable to allow Validation to be sent around activity fragments
 */

/* REFERENCES
 * Regex checks for validation learned from AccountCreation.java (one of my previous classes that dealt with password complexity)
 * And many more from https://developer.android.com/
 *
 */

public class Validation implements Serializable
{
    
    public enum Validate_Result
    {
        
        Pass, Fail, TooBig, NoCapitals, NoLowers, NoNumbers, NoSpecial, NoOthers, NoNull, BadLength, TooShort

    }

    //Constants

    private final Map<Validate_Result, String> errors; //Map containing all error messages for each enum above
    //These are the constants for username and password restrictions (placed up here for easy edit)
    //Passwords and usernames need tight restrictions which we enforce here (to stop manipulation of those checks) but free input allows you to specify restrictions since that type of input can be vastly varied
    private final int MINLENGTH = 1; //Length is the same for username/password to keep things uniform (and 20 characters is enough for both to be complex)
    private final int MAXLENGTH = 20;
    private final boolean[] UCHARPERM = {true, true, true, false}; //Permission arrays to denote what characters are allowed in each type
    private final boolean[] PCHARPERM = {true, true, true, true};

    public Validation()
    {
        //These are declared in the constructor so they are destroyed after construction to lower overhead
        final Validate_Result[] eNames = {Validate_Result.Fail, Validate_Result.TooBig, Validate_Result.NoCapitals, Validate_Result.NoLowers,
                                    Validate_Result.NoNumbers, Validate_Result.NoSpecial, Validate_Result.NoOthers, Validate_Result.NoNull,
                                    Validate_Result.BadLength, Validate_Result.TooShort};
        final String[] eMessages = {"Something major has failed within this input.", "This input is above the maximum length." /*Consider changing the way Big/Short errors work to display the require length to meet*/
                                , "No uppercase characters are allowed.", "No lowercase characters are allowed.", "No numbers are allowed.", "No special characters are allowed."
                                , "No unsanctioned characters (anything but ' ,.\"\'!?& ') are allowed.", "An entry must be submitted.",
                                "Minimum length must be less than or equal to maximum.", "This input is below the minimum length."}; //CONSIDER REWORKING THESES TO MORE USER FRIENDLY MESSAGES
        Map<Validate_Result, String> messages = new HashMap<Validate_Result, String>();
        if(eNames.length == eMessages.length) //Make sure the enums have an equal number of messages
        {
            for(int ii = 0; ii < eNames.length; ii++)
            {

                messages.put(eNames[ii], eMessages[ii]);

            }
        }
        else
        {
            throw new RuntimeException("Validation errors are out of sync - errors will not work");
        }

        errors = messages;
    }

    /* FUNCTION INFORMATION
     * NAME - validateLength
     * INPUTS - input (string that needs to be checked), min/maxLen (how long the string should be)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs length checks on an input string to see if it is too long/short than it should be
     */
    private Validate_Result validateLength(String input, int minLen, int maxLen)
    {
        
        Validate_Result result; //Here in case of errors
        int length = input.length();

        //Build as an if-else since if the first too checks pass the string must of an appropriate length
        if(length < minLen)
        {
            result =  Validate_Result.TooShort; //We do individual checks rather than a min < len > max so we can return unique errors for each case
        }
        else if(length > maxLen) //Could include a comparison that if maxLen == -1 then no maximum bound so skip check
        {
            result = Validate_Result.TooBig;
        }
        else
        {
            result = Validate_Result.Pass;
        }

        return result;
        
    }

    /* FUNCTION INFORMATION
     * NAME - validateCharacters
     * INPUTS - input (string that needs to be checked), allowX (booleans denoting whether or not the input string is allowed to have that character type in it), currentResult (here to allow final check to work - see below)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs regex operations to identify if an input string contains characters it is not meant to have
     */
    private Validate_Result validateCharacters(String input, boolean allowUCase, boolean allowLCase, boolean allowNum, boolean allowSpecial, Validate_Result currentResult)
    {

        Validate_Result result = currentResult;

        //The first three checks only perform validation if the boolean is false (means we need to check if they are in input - since they agent allowed to be there)
        //If the boolean is true then we don't care about the content since we are not enforcing the use of Upper/Lower/Number in a input string
        //NOTE - the enforcement of complexity (passwords/usernames etc.) must be done in the class that generates the input - since it it technically not a correct
        //input until those complexity requirements are satisfied and we only validate seemingly "valid" inputs
        if(allowUCase == false)
        {
            if(input.matches(".*[A-Z].*"))
            {
                result = Validate_Result.NoCapitals;
            }
            else
            {
                result = Validate_Result.Pass;
            }
        }

        if(allowLCase == false)
        {
            if(input.matches(".*[a-z].*"))
            {
                result = Validate_Result.NoLowers;
            }
            else
            {
                result = Validate_Result.Pass;
            }
        }

        if(allowNum == false)
        {
            if(input.matches(".*[0-9].*"))
            {
                result = Validate_Result.NoNumbers;
            }
            else
            {
                result = Validate_Result.Pass;
            }
        }

        if(allowSpecial == false)
        {
            if(input.matches(".*[,.\"\'!?&]")) //List of characters we would allow (Edit if you wish to allow/restrict more)
            {
                result = Validate_Result.NoSpecial;
            }
            else
            {
                result = Validate_Result.Pass;
            }
        }

        if(result == Validate_Result.Pass) //If no other error was found - else we want to return that error (if we set the result to fail at the start - an input that accepts all four types of characters would fail even if it was correct) since this is skipped
        {
            //Special characters has an additional check since while we may allow special characters - we do not allow all of them (removed for both simplicity/clarity of
            //names/passwords as well as to enhance security by restricting use of characters that can give a lot of power in code (brackets, asterisks, slashes etc)
            if(input.matches(".*[^0-9A-Za-z,.\"\'!?&].*"))
            {
                result = Validate_Result.NoOthers;
            }
            else
            {
                result = Validate_Result.Pass;
            }
        }

        return result;
        
    }

    /* FUNCTION INFORMATION
     * NAME - getValidatorError
     * INPUTS - result (the Validate_Result enum that the calling class got and wants to find the error for)
     * OUTPUTS - message (a string of the error message the result enum described)
     * PURPOSE - This is the function that takes a Validate_Result another class got and returns an error message so that class can display it to the user
     */
    public String getValidatorError(Validate_Result result)
    {
        
        String message = "";
        
        message = errors.get(result); //Grab the error message that is tied to this key
        
        return message;
        
    }

    /* FUNCTION INFORMATION
     * NAME - validateUsername
     * INPUTS - input (string that needs to be checked)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs validation checks on any username the user submits/creates
     */
    public Validate_Result validateUsername(String input)
    {
        
        Validate_Result result = Validate_Result.Fail;

        if(input == null) //Do a null check before attempting to check string contents
        {
            result = Validate_Result.NoNull;
        }
        else
        {
            result =  validateLength(input, MINLENGTH, MAXLENGTH); //Check the length (edit constants to your required length)
            if (result == Validate_Result.Pass) //Only do next check if passed - otherwise we want to return the error
            {
                result = validateCharacters(input, UCHARPERM[0], UCHARPERM[1], UCHARPERM[2], UCHARPERM[3], result); //Edit constants to match what you require in a username
            }
        }

        return result;
        
    }

    /* FUNCTION INFORMATION
     * NAME - validatePassword
     * INPUTS - input (string that needs to be checked)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs validation checks on any password a user inputs/creates
     * NOTE - Password hashing is not done here and is not done before coming here (since hashed passwords would be impossible to properly regex check). So this must
     *        be done AFTER the result comes back as a PASS
     */
    public Validate_Result validatePassword(String input)
    {

        Validate_Result result = Validate_Result.Fail;

        if(input == null) //Do a null check before attempting to check string contents
        {
            result = Validate_Result.NoNull;
        }
        else
        {
            result =  validateLength(input, MINLENGTH, MAXLENGTH); //Check the length (edit constants to your required length)
            if (result == Validate_Result.Pass) //Only do next check if passed - otherwise we want to return the error
            {
                result = validateCharacters(input, PCHARPERM[0], PCHARPERM[1], PCHARPERM[2], PCHARPERM[3], result); //Edit constants to match what you require for a password
            }
        }

        return result;

    }

    /* FUNCTION INFORMATION
     * NAME - validateFreeInput
     * INPUTS - input (string that needs to be checked), XLen (integers denoting the desired length restrictions), allowX (booleans denoting whether or not the input string is allowed to have that character type in it)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs validation checks on any free input a user submits in the entire application
     */
    public Validate_Result validateFreeInput(String input, int minLen, int maxLen, boolean allowUCase, boolean allowLCase, boolean allowNum, boolean allowSpecial)
    {

        Validate_Result result = Validate_Result.Fail;

        if(input == null) //Do a null check before attempting to check string contents
        {
            result = Validate_Result.NoNull;
        }
        else if (minLen > maxLen) //If the lengths are in the wrong order/not set properly (if the are equal doesn't matter - means string must be an exact size)
        {
            result = Validate_Result.BadLength;
        }
        else
        {
            result =  validateLength(input, minLen, maxLen); //Check the length (edit to your required length)
            if (result == Validate_Result.Pass) //Only do next check if passed - otherwise we want to return the error
            {
                result = validateCharacters(input, allowUCase, allowLCase, allowNum, allowSpecial, result); //Edit to match what you require in complete free input (descriptions etc with no complexity restrictions)
            }
        }

        return result;

    }
    
}
