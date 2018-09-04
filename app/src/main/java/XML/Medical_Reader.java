package XML;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

//Todo need to look up catching if a attribute is present as dates attributes will determine the entry, and differentiate between them
class Medical_Reader implements XML_Reader {
    /**
     * Read file map.
     *
     * @param input_Stream Represents the FileInputStream Object used to read users data file stored on the device
     * @param tags         the tags to read from the XML file specified
     * @return a Map with string pair values, with Tag name attached to the value read in, if empty it will be 'NaN' value
     */
    @Override
    public Map<String, String> Read_File(FileInputStream input_Stream, List<Tags_To_Read> tags) throws NullPointerException, XML_Reader_Exception {
        return null;
    }
}
