package MedicalStates;

import android.content.Context;

import java.io.File;

import Factory.Factory;
import XML.XML_Writer_Failure_Exception;
import XML.XML_Writer_File_Layout_Exception;

/**
 * <h1>StomaState</h1>
 * This interface provides a way to access the current three provided states for the state
 * calculator. This interface exists so states can be added, changed or removed without having to
 * make major changes to the state calculator class
 *
 * @author Ethan Bell
 * @version 1.0
 *
 */
public interface StomaState {
    /**
     * Allows for interactions with the android system that will change depending on the state
     * @return boolean representing success/failure
     */
    boolean Account_State_Information(Factory factory, File acc) throws XML_Writer_File_Layout_Exception, XML_Writer_Failure_Exception;

    /**
     * Returns the numeric value of the current state
     * @return Real number representation of the current state value
     */
    double getStateVal();

    /**
     * Returns the current state in string format
     * @return String representation of the current state
     */
    String getState();
}
