package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Check_State implements State_Observer {

    // TODO: 27-Aug-18 WHen Factory Class created uncomment 
//private Factory factory;

    public Check_State(/*Factory factory*/) {
        //this.factory = factory;
    }

    @Override
    public boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream) throws NullPointerException{
        if (input_Stream != null && output_Stream != null) {
        boolean valid = false;
            // TODO: 27-Aug-18 Uncomment when state Calculator package is created with classes
        //Stoma_State_Calculator stoma_state_calculator = Factory.Create_Stoma_State_Calculator();
        //stoma_state_calculator.Calculate_State();
        return valid;
        } else {
            if(input_Stream == null){
                throw new NullPointerException("Input Stream Object is Null");
            }else{
                throw new NullPointerException("Output Stream Object is Null");
            }
        }
    }
}
