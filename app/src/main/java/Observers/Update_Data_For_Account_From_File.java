package Observers;

import android.content.Context;

/**
 * Created by Patrick on 25-Aug-18.
 * Last Edited by Patrick on 25-Aug-18 8:20pm
 *
 * Changes:
 * 25th Aug
 * Created 'Update_Data_For_Account_From_File', and added 'Update_Information' method
 * Added Comment Block
 */

public class Update_Data_For_Account_From_File implements Update_Data {

    //private Factory factory;
// TODO: 25-Aug-18 When factory class is created uncomment
    public Update_Data_For_Account_From_File(/*Factory factory*/) {
        //this.factory = factory;
    }

    // TODO: 25-Aug-18 get information from file through context, may need a file reading from xml. Use androids 1 if possible. 
    @Override
    public String[] Update_Information(Context context, String file_Name) {
        return new String[0];
    }
}
