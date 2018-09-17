package XML;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


//Note Have to use Document Builder and DOM as need to read all existing first and add in new then overwrite all
// http://www.java2s.com/Tutorials/Java/XML_HTML_How_to/DOM/Append_a_node_to_an_existing_XML_file.htm

class Account_Writer implements XML_Writer {
    /**
     * @param account_File   Represents the FileOutputStream Object used to write users data to the specified file stored on the device
     * @param values         Map with string pair values, where the Keys correlate to the Enum Tags_To_Write values
     * @param tags           Div Tags to write the values to in file
     * @return               True if successful otherwise false
     */
    @Override
    public Boolean Write_File(File account_File, Map<String, String> values, List<Tags_To_Write> tags) throws IOException, ParserConfigurationException, SAXException {
        Boolean success = false;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(account_File);

return success;
    }
}
