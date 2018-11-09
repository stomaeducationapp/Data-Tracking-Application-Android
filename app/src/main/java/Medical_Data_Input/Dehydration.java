package Medical_Data_Input;

/**
 * <h1>Dehydration</h1>
 * The Dehydration Java Class holds all data which describes the user's dehydration levels
 * Owned by the StomaForm Class
 * @author Casey Rogers
 * @version 1.0
 * <h1>Changes:</h1>
 * 2nd October
 * Created Dehydration Class which is owned by he StomaForm class
 * <p>
 * Insert new changes here
 * <h>NOTE</h>
 */

public class Dehydration {

    private String[] symptoms;

    /**
     * @param symptomsInput Represents the symptoms of a user to describe their dehydration levels
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the bag input is invalid
     */
    public boolean setDehydration(String[] symptomsInput) throws IndexOutOfBoundsException
    {

        boolean dataValidation;
        dataValidation = true;

        for(String value : symptomsInput)
        {
            if(value == null)
            {
                dataValidation = false;
            }
        }

        return dataValidation;
    }

    public String[] getSymptoms()
    {
        return symptoms;
    }
}
