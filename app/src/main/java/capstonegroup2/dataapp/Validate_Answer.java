package capstonegroup2.dataapp;

import java.util.regex.Pattern;

import XML.Account_Reader;

/**
 * <h1>Validate_Answer</h1>
 * Contains functions to sanitize and ensure user's entered answers have been entered properly.
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