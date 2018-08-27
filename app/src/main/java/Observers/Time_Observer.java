package Observers;

import java.io.FileInputStream;

public interface Time_Observer {


    /**
     * @param input_Stream
     * @return
     * @throws NullPointerException
     */
    boolean Notify(FileInputStream input_Stream) throws NullPointerException;
}
