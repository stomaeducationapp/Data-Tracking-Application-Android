package Observers;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public interface State_Observer {
    //String return can be replaced with Enum if required for which state we are in, but also may need to tell if state hasn't changed
    boolean Notify(FileInputStream input_Stream, FileOutputStream output_Stream)throws NullPointerException;
}
