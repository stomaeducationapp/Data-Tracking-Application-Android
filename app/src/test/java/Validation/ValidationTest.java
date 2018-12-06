package Validation;

import org.junit.Test;

import Factory.Factory;

import static org.junit.Assert.assertEquals;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 5/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 5/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is the test file for all testing related to the Validator class
 */

/* VERSION HISTORY
 * 5/12/2018 - Created test harnesses and completed testing
 */

/* REFERENCES
 * Android studio test research on https://drive.google.com/open?id=11L9vChloJtNgOaYAJQpCSJlA2hajMQCe
 * And all related documentation on https://developer.android.com
 */

public class ValidationTest
{

    //Classfields
    private Factory f = Factory.Get_Factory();

    /* FUNCTION INFORMATION
     * NAME - testInputOne
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on possible cases for the input check when input string is null
     */
    @Test
    public void testInputOne()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername(null);
        assertEquals("Null error was returned", Validation.Validate_Result.NoNull, result);

        result = v.validatePassword(null);
        assertEquals("Null error was returned", Validation.Validate_Result.NoNull, result);

        result = v.validateFreeInput(null, 1, 1, true, true, true, true);
        assertEquals("Null error was returned", Validation.Validate_Result.NoNull, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "An entry must be submitted.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testLengthOne
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on the possible cases for the length import parameters being incorrect and related error message
     */
    @Test
    public void testLengthOne()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateFreeInput("hello", 10, 1, true, true, true, true);
        assertEquals("Bad Length error was returned", Validation.Validate_Result.BadLength, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "Minimum length must be less than or equal to maximum.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testLengthTwo
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on possible cases for the length when string is empty
     */
    @Test
    public void testLengthTwo()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername("");
        assertEquals("Too short error was returned", Validation.Validate_Result.TooShort, result);

        result = v.validatePassword("");
        assertEquals("Too short error was returned", Validation.Validate_Result.TooShort, result);

        result = v.validateFreeInput("", 1, 1, true, true, true, true);
        assertEquals("Too short error was returned", Validation.Validate_Result.TooShort, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "This input is below the minimum length.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testLengthThree
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on possible cases for the length when string is less than minLen
     */
    @Test
    public void testLengthThree()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateFreeInput("abcd", 5, 10, true, true, true, true);
        assertEquals("Too short error was returned", Validation.Validate_Result.TooShort, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "This input is below the minimum length.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testLengthFour
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on possible cases for the length being bigger than maxLen
     */
    @Test
    public void testLengthFour()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername("HelloHelloHelloHelloHello");
        assertEquals("Too big error was returned", Validation.Validate_Result.TooBig, result);

        result = v.validatePassword("HelloHelloHelloHelloHello");
        assertEquals("Too big error was returned", Validation.Validate_Result.TooBig, result);

        result = v.validateFreeInput("Hello", 1, 3, true, true, true, true);
        assertEquals("Too big error was returned", Validation.Validate_Result.TooBig, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "This input is above the maximum length.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testLengthFive
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on possible cases for the length when correct string entered
     */
    @Test
    public void testLengthFive()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername("Hello");
        assertEquals("Pass was returned", Validation.Validate_Result.Pass, result);

        result = v.validatePassword("Hello");
        assertEquals("Pass was returned", Validation.Validate_Result.Pass, result);

        result = v.validateFreeInput("Hello", 1, 10, true, true, true, true);
        assertEquals("Pass was returned", Validation.Validate_Result.Pass, result);

        //Since no errors on pass - no check

    }

    /* FUNCTION INFORMATION
     * NAME - testCharactersOne
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on character validation when input has uppercase letters when allowUpper is false
     */
    @Test
    public void testCharactersOne()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateFreeInput("Hello", 1, 10, false, true, true, true);
        assertEquals("No capitals error was returned", Validation.Validate_Result.NoCapitals, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "No uppercase characters are allowed.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testCharactersTwo
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on character validation when input has lowercase letters when allowLower is false
     */
    @Test
    public void testCharactersTwo()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateFreeInput("Hello", 1, 10, true, false, true, true);
        assertEquals("No lowers error was returned", Validation.Validate_Result.NoLowers, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "No lowercase characters are allowed.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testCharactersThree
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on character validation when input has numbers when allowNum is false
     */
    @Test
    public void testCharactersThree()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateFreeInput("Hello9", 1, 10, true, true, false, true);
        assertEquals("No numbers error was returned", Validation.Validate_Result.NoNumbers, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "No numbers are allowed.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testCharactersFour
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on character validation when input has allowed special characters but allowSpecial is false
     */
    @Test
    public void testCharactersFour()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername("Hello?"); //Usernames are not allowed special in current build
        assertEquals("No special error was returned", Validation.Validate_Result.NoSpecial, result);

        result = v.validateFreeInput("Hello?", 1, 10, true, true, true, false);
        assertEquals("No special error was returned", Validation.Validate_Result.NoSpecial, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "No special characters are allowed.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testCharactersFive
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on character validation when input has unsanctioned special characters (not counted in allowSpecial)
     */
    @Test
    public void testCharactersFive()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername("Hello@#");
        assertEquals("No other characters error was returned", Validation.Validate_Result.NoOthers, result);

        result = v.validatePassword("Hello@#");
        assertEquals("No other characters error was returned", Validation.Validate_Result.NoOthers, result);

        result = v.validateFreeInput("Hello@#", 1, 10, true, true, true, true);
        assertEquals("No other characters error was returned", Validation.Validate_Result.NoOthers, result);

        //Since both errors are the same - only need to do one error check
        assertEquals("Error message correct", "No unsanctioned characters (anything but ' ,.\"\'!?& ') are allowed.", v.getValidatorError(result));

    }

    /* FUNCTION INFORMATION
     * NAME - testCharactersSix
     * INPUTS - none
     * OUTPUTS - none
     * PURPOSE - These are the tests that performs asserts on character validation when input is correct
     */
    @Test
    public void testCharactersSix()
    {

        Validation v = f.Make_Validation();
        Validation.Validate_Result result;

        result = v.validateUsername("H3!!0");
        assertEquals("Pass was returned", Validation.Validate_Result.Pass, result);

        result = v.validatePassword("H3!!0");
        assertEquals("Pass was returned", Validation.Validate_Result.Pass, result);

        result = v.validateFreeInput("H3!!0", 1, 10, true, true, true, true);
        assertEquals("Pass was returned", Validation.Validate_Result.Pass, result);

        //Since no errors on pass - no check

    }

}
