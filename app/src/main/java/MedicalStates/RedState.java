package MedicalStates;

import android.content.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import XML.Account_Writer;
import XML.Medical_Writer;
import XML.XML_Writer;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;

/**
 * <h1>RedState</h1>
 * This class encompasses the functionality specific to the red state.
 *
 * @author Ethan Bell
 * @version 1.1
 *
 */
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
    //TODO: Verify if writer working in integration
    @Override
    public boolean Account_State_Information(Factory factory, File med) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception {
        boolean success;
        Medical_Writer mw = (Medical_Writer) factory.Make_Writer(Factory.XML_Writer_Choice.Medical);
        XML_Writer.Tags_To_Write job = XML_Writer.Tags_To_Write.Modify;

        Map<String, String> content = new HashMap<>();
        content.put("Medical_State", Double.toString(stateVal));

        success = mw.Write_File(med, content, job); //Update the state in the system

        return success;
    }
}
