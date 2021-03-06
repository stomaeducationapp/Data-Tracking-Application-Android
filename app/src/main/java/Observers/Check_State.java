package Observers;

import java.io.File;

import Factory.Factory;
import MedicalStates.StomaStateCalculator;

/**
 * <h>Check_State</h>
 * The Check_State Java Class is used to trigger code required to calculate the current medical 'state'
 * the current user is in. This Observer has been created to reduce the coupling that would be required
 * between the Medical Data Input Package and Medical States Package.
 * Implements State_Observer
 *
 * @author Patrick Crockford
 * @version 1.1 <h>Changes</h1>
 * <h1>Last Edited</h1>
 * 25th Feb 2019
 * Jeremy Dunnet
 */
public class Check_State implements State_Observer {


    private Factory factory;

    /**
     * Instantiates a new Check_State.
     */
    public Check_State(Factory factory) {
        this.factory = factory;
    }

    /**
     * @param medical Represents the File Object of the medical information file for the account currently logged in
     * @return True if state was successfully calculated and written to file, otherwise false
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(File medical) throws NullPointerException {
        if (medical != null) {
            boolean valid;
            StomaStateCalculator stoma_state_calculator = factory.Make_Stoma_State_Calculator();
            valid = stoma_state_calculator.Calculate_State(medical);
            return valid;
        } else {
            throw new NullPointerException("Medical File Object is Null");
        }
    }
}
