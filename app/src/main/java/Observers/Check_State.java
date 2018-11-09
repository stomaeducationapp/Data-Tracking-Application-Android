package Observers;

import java.io.File;

import Factory.Factory;

/**
 * <h>Check_State</h>
 * The Check_State Java Class is used to trigger code required to calculate the current medical 'state'
 * the current user is in. This Observer has been created to reduce the coupling that would be required
 * between the Medical Data Input Package and Medical States Package.
 * Implements State_Observer
 *
 * @author Patrick Crockford
 * @version 1.0 <h>Changes</h1>
 * <h1>Last Edited</h1>
 * 17 Oct 2018
 * Patrick Crockford
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
     * @param account Represents the File Object of the account information file for the account currently logged in
     * @return True if state was successfully calculated and written to file, otherwise false
     * @throws NullPointerException if input_Stream and/or output_Stream Objects are null
     */
    @Override
    public boolean Notify(File medical, File account) throws NullPointerException {
        if (medical != null && account != null) {
            boolean valid = false;
            // TODO: 17-Sep-18 Uncomment and modify when State Calculator package has been created
            //Stoma_State_Calculator stoma_state_calculator = Factory.Create_Stoma_State_Calculator();
            //stoma_state_calculator.Calculate_State();
            return valid;
        } else {
            if (medical == null) {
                throw new NullPointerException("Medical File Object is Null");
            } else {
                throw new NullPointerException("Account File Object is Null");
            }
        }
    }
}
