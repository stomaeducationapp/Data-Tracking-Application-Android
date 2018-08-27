package Observers;

import java.io.FileInputStream;

public class Export_Data implements Time_Observer {

    //private Factory factory;

    public Export_Data(/*Factory factory*/) {
        //this.factory = factory;
    }

    /**
     * @param input_Stream
     * @return
     * @throws NullPointerException
     */
    @Override
    public boolean Notify(FileInputStream input_Stream) throws NullPointerException {
        if (input_Stream != null) {
            boolean valid = false;
            // TODO: 27-Aug-18 Uncomment when export package is created
        //Export_Handler export_handler = factory.Create_Export_Handler();
        //valid = export_handler.Export_Data(input_Stream);
            return valid;
        } else {
            throw new NullPointerException("Input Stream Object is Null");
        }
    }
}
