package ValidationClasses;

import java.util.regex.Pattern;

/**
 * <h1>Validate_Answer</h1>
 * Contains functions to sanitize and ensure user's entered answers have been entered properly.
 *
 * Purpose: Class aim is to check for correct parameters and sanitise input. Class is to be
 * instantiated and the general function isAnswerValid called. The calling program will pass
 * the value to be checked and the function will return an enum value. The potential enum return
 * values are:
 *     GOOD - The input argument is correct as per requirements
 *     BADLENGTH - The input argument does not match the required size
 *     BADCHAR - The input argument contains characters that are not approved, such as '!' or '<'
 *     BADCODE - There is an attempt at code injection in the input argument (feature in progress)
 *     BADOTHER - There is an unspecified problem with the input argument
 *
 * Progress: Specific implementation needs to occur. Could not complete by end of project deadline.
 * Current implementation is example only.
 *
 * @author Oliver Yeudall
 * @version 1.0
 */
public class Validate_Answer {

    private static final int minLength = 1; // Minimum allowable answer length
    public enum retMessage{
        GOOD, BADLENGTH, BADCHAR, BADCODE, BADOTHER
    }

    /**
     * Default Constructor
     * DESCRIPTION: Sets all string field variables to empty strings
     */
    Validate_Answer() {

    }

    /**
     *
     * @return boolean which indicates that an answer has at least 1 character in it
     */
    private boolean validLength(String inAnswer) {

        String testAnswer = inAnswer;
        boolean isValid = false;

        if (testAnswer.length() != null) {
            if (testAnswer.length() > minLength) {
                isValid = true;
            }
        }

        return isValid;
    }

    /**
     * @return boolean representing an answer with the approved used character set
     */
    private boolean validCharacters(String inAnswer) {

        String testAnswer = inAnswer;
        boolean isValid = false;

        if(Pattern.matches("[a-zA-Z]",testAnswer)) {
            isValid = true;
        }



        return isValid;
    }

    /**
     * @return boolean representing an answer without executable code
     */
    private boolean validNoCode(String inAnswer) {

        String testAnswer = inAnswer;
        boolean isValid = true;

        return isValid;
    }

    /**
     * @return enum value returning the results of the answer validation test
     */
    public boolean isAnswerValid(String inAnswer){

        String testAnswer = inAnswer;

        if (validLength(testAnswer)) {
            if(validCharacters(testAnswer)) {
                if(validNoCode(testAnswer)) {
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
        else {
            return retMessage.BADLENGTH;
        }

        return retMessage.BADOTHER;
    }
}