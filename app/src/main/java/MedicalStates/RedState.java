package MedicalStates;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import XML.Account_Writer;
import XML.XML_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;

public class RedState implements StomaState {
    private double stateVal;

    /**
     * Constructor for the red state class. Validates the imported value before executing.
     * @param inStateVal The value representing the state to switch to. Used to double check the
     *                   correct state was selected before setting it.
     */
    public RedState(int inStateVal) {
        if (inStateVal > 7 && inStateVal < 11) {
            stateVal = (double)inStateVal;
        }
        else {
            throw new IllegalArgumentException("Red state must be between 8 and 10");
        }
    }

    /**
     * Returns the real number representation of the current state
     * @return the value assigned to the current state
     */
    @Override
    public double getStateVal() {
        return stateVal;
    }

    /**
     * Returns the name of the current state
     * @return String representation of Red state
     */
    @Override
    public String getState() {
        return "Red";
    }

    /**
     * This method is to handle any specific android system calls that must be done in the red state.
     * It has been left empty as the exact purpose is not yet known
     * @return boolean representing success/failure
     */
    //TODO: Verify if working in integration
    @Override
    public boolean Account_State_Information(Factory factory, File acc) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception {
        Account_Writer writer = factory.Make_Account_Writer();

        Map<String, String> content = new HashMap<>();

        content.put("State", Double.toString(stateVal));

        writer.Write_File(acc, content, XML_Writer.Tags_To_Write.State);
        return true;
    }
}
