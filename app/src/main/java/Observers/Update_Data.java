package Observers;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by Patrick on 25-Aug-18.
 * Last Edited by Patrick on 25-Aug-18 8:20pm
 * <p>
 * Changes:
 * 25th Aug
 * Created Interface 'Update_Data', and created 'Update_Information' method
 * Added Comment Block
 */

public interface Update_Data {

    Map<String, String> Update_Information(InputStream inputStream, String file_Name) throws NullPointerException;
}
