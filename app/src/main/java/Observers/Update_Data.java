package Observers;

import android.content.Context;

/**
 * Created by Patrick on 25-Aug-18.
 * Last Edited by Patrick on 25-Aug-18 8:20pm
 *
 * Changes:
 * 25th Aug
 * Created Interface 'Update_Data', and created 'Update_Information' method
 * Added Comment Block
 */

public interface Update_Data {

    String[] Update_Information(Context context, String file_Name);
}
