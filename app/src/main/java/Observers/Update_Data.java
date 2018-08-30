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

    /**
     * Update information map.
     *
     * @param inputStream the input stream
     * @return the map
     * @throws NullPointerException the null pointer exception
     */
    Map<String, String> Update_Information(InputStream inputStream) throws NullPointerException;
}
