package Medical_Data_Input;

/**
 * <h1>Urine</h1>
 * The Urine Java Class which will be the container holding all data entered for urine
 * Owned by the StomaForm Class
 * @author Casey Rogers
 * @version 1.0
 * <h1>Changes:</h1>
 * 2nd October
 * Created Urine Class which is owned by he StomaForm class
 * <p>
 * 5th February
 * Updated class to have specific constructor and fixed setColour - Jeremy Dunnet
 */

public class Urine {

    private String amount;
    private String colour;

    public Urine(String inAmount, String inColour)
    {

        amount = null; //If either set fails - other classes can check by getting the value and checking if null
        colour = null;

        setAmount(inAmount);
        setColour(inColour);
    }

    /**
     * @param amountInput Represents the amount of time a person has urinated
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the bag input is invalid
     */
    public boolean setAmount(String amountInput) throws IndexOutOfBoundsException
    {

        boolean dataValidation;
        int iAmount;

        iAmount = Integer.parseInt(amountInput);

        //Assuming max is 15 time
        if(iAmount < 0 || iAmount > 15)
        {
            dataValidation = false;
        }
        else
        {
            amount = amountInput;
            dataValidation = true;
        }
        return dataValidation;
    }

    /**
     * @param colourInput Represents the amount of time a person has urinated
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the bag input is invalid
     */
    public boolean setColour(String colourInput) throws IndexOutOfBoundsException
    {

        boolean dataValidation;
        dataValidation = true;

        //Data which can be given:
        //Dark, Normal, Light, Clear

        String[] colours = {"Dark", "Normal", "Light", "Clear"};

        for(int ii = 0 ; ii < colours.length; ii++)
        {
            if (!colourInput.equals(colours[ii])) {
                dataValidation = false;
            }
        }

        if(dataValidation == true)
        {
            colour = colourInput;
        }

        return dataValidation;
    }

    public String getAmount()
    {
        return amount;
    }

    public String getColour()
    {
        return colour;
    }
}
