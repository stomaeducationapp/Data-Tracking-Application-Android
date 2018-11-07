package XML;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class Account_Reader_unit_Test {

    XML_Reader xml_Reader;
    List<XML_Reader.Tags_To_Read> list;
    @Before
    public void setup() {
        xml_Reader = new Account_Reader();
        list = new LinkedList<>();
    }


    @Test
    public void Check_File_Stream() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("empty_file.xml");
        assertNotNull(is);
    }

    @Test
    public void Empty_File_Parse() throws IOException, XmlPullParserException {
        list.add(XML_Reader.Tags_To_Read.Account_Name);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("empty_file.xml");
        XmlPullParser xmlPullParser = Mockito.mock(XmlPullParser.class);
        Mockito.when(xmlPullParser.getName()).thenReturn("a");
        Mockito.when(xmlPullParser.next()).thenReturn(XmlPullParser.END_DOCUMENT);
        try {
            Map<String, String> map = xml_Reader.Read_File(xmlPullParser, list, null);
            assertNotNull(map);
        } catch (XML_Reader_Exception e) {
            e.printStackTrace();
        }
    }
}