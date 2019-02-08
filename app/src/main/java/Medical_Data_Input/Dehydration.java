package Medical_Data_Input;

/**
 * <h1>Dehydration</h1>
 * The Dehydration Java Class holds all data which describes the user's dehydration levels
 * Owned by the StomaForm Class
 * @author Casey Rogers
 * @version 1.1
 * <h1>Changes:</h1>
 * 2nd October
 * Created Dehydration Class which is owned by he StomaForm class
 * <p>
 * 5th February
 * Updated class to have specific constructor and fixed setSymptoms - Jeremy Dunnet
 * <p>
 * 8th February
 * Updated class methods after MedicalInput was changed - Jeremy Dunnet
 */

public class Dehydration {

    private String[] symptoms;

    public Dehydration(String[] inSymptoms)
    {
        symptoms = null; //If set fails - other classes can check by getting the value and checking if null

        setDehydration(inSymptoms);
    }

    /**
     * @param symptomsInput Represents the symptoms of a user to describe their dehydration levels
     * @return True if the input data is valid, otherwise false.
     * @throws IndexOutOfBoundsException if the bag input is invalid
     */
    public boolean setDehydration(String[] symptomsInput) throws IndexOutOfBoundsException
    {

        boolean dataValidation;
        dataValidation = false;

        String[] s = {"Thirsty","Headache", "Light Headed", "Stomach Cramps", "Muscle Cramps", "Fatigue", "Dry Mouth", "Confusion", "Tiredness"};

        if(symptomsInput.length > 0) //If symtpoms were recorded - 0 is a valid answer
        {
            for(String sympt : symptomsInput)
            {
                for(int ii = 0 ; ii < s.length; ii++)
                {
                    if (sympt.equals(s[ii])) {
                        dataValidation = true;
                    }
                }
            }
        }
        else if (symptomsInput.length == 0)
        {
            dataValidation = true;
        }

        if(dataValidation == true)
        {
            symptoms = symptomsInput;
        }

        return dataValidation;
    }

    public String[] getSymptoms()
    {
        return symptoms;
    }
}
