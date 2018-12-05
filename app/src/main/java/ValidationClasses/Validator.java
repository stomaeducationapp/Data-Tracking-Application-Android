package ValidationClasses;

import java.util.HashMap;
import java.util.Map;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 4/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 4/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the class that handles input validation (process of checking user input for non-allowed characters)
 * to ensure any input that is stored/passed to important classes is not malicious in nature.
 */

/* VERSION HISTORY
 * 4/12/2018 - Created basic code setup from previous iteration by Oliver Yeudall (Combined since splitting into separate classes seemed redundant)
 */

/* REFERENCES
 * Regex checks for validation learned from AccountCreation.java (one of my previous classes that dealt with password complexity)
 * And many more from https://developer.android.com/
 */

public class Validator 
{
    
    enum Validate_Result
    {
        
        Pass, Fail, TooBig, NoCapitals, NoLowers, NoNumbers, NoSpecial, NoOthers, TooShort

    }
    
    private final Map<Validate_Result, String> errors;

    public Validator()
    {
        //These are declared in the constructor so they are destroyed after construction to lower overhead
        final Validate_Result[] eNames = {Validate_Result.Fail, Validate_Result.TooBig, Validate_Result.NoCapitals, Validate_Result.NoLowers,
                                    Validate_Result.NoNumbers, Validate_Result.NoSpecial, Validate_Result.NoOthers, Validate_Result.TooShort};
        final String[] eMessages = {"Something major has failed within this input.", "This input is above the maximum length." /*Consider changing the way Big/Short errors work to display the require length to meet*/
                                , "No uppercase characters are allowed.", "No lowercase characters are allowed.", "No numbers are allowed.", "No special characters are allowed."
                                , "No unsanctioned characters (anything but ' ,.\"\'!?& ') are allowed.", "This input is below the minimum length."}; //CONSIDER REWORKING THESES TO MORE USER FRIENDLY MESSAGES
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
        
        Validate_Result result = Validate_Result.Fail; //Here in case of errors
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
     * INPUTS - input (string that needs to be checked), allowX (booleans denoting whether or not the input string is allowed to have that character type in it)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs regex operations to identify if an input string contains characters it is not meant to have
     */
    private Validate_Result validateCharacters(String input, boolean allowUCase, boolean allowLCase, boolean allowNum, boolean allowSpecial)
    {

        Validate_Result result = Validate_Result.Fail;

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

        result =  validateLength(input, 5, 20); //Check the length (edit to your required length)
        if (result == Validate_Result.Pass) //Only do next check if passed - otherwise we want to return the error
        {
            result = validateCharacters(input, true, true, true, false); //Edit to match what you require in a username
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

        result =  validateLength(input, 10, 20); //Check the length (edit to your required length)
        if (result == Validate_Result.Pass) //Only do next check if passed - otherwise we want to return the error
        {
            result = validateCharacters(input, true, true, true, true); //Edit to match what you require for a password
        }

        return result;

    }

    /* FUNCTION INFORMATION
     * NAME - validateFreeInput
     * INPUTS - input (string that needs to be checked)
     * OUTPUTS - result (the Validate_Result enum tat details the status of the check (failure error/pass)
     * PURPOSE - This is the function that performs validation checks on any free input a user submits in the entire application
     */
    public Validate_Result validateFreeInput(String input)
    {

        Validate_Result result = Validate_Result.Fail;

        result =  validateLength(input, 1, 10000); //Check the length (edit to your required length)
        if (result == Validate_Result.Pass) //Only do next check if passed - otherwise we want to return the error
        {
            result = validateCharacters(input, true, true, true, true); //Edit to match what you require in complete free input (descriptions etc with no complexity restrictions)
        }

        return result;

    }
    
}
