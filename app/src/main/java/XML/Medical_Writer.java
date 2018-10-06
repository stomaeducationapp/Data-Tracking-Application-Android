package XML;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

public class Medical_Writer implements XML_Writer {
    /**
     * @param account_File Represents the File Object used to write users data to the specified file stored on the
     *                     device
     * @param values       Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @param enum_Tags         Div Tags to write the values to in file
     * @return True if successful otherwise false
     */

    //For creating a new document
    //https://www.tutorialspoint.com/java_xml/java_dom_create_document.htm
    //then merge existing one from file into it!!!! for adding a new entry
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, List<Tags_To_Write> enum_Tags) throws IOException, ParserConfigurationException, SAXException {
        return null;
    }
}
