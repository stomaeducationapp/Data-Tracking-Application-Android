package Validation.OldFiles;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import XML.Account_Reader;
import XML.XML_Reader;

import static Validation.OldFiles.Validate_Username.retMessage.BADCHAR;
import static Validation.OldFiles.Validate_Username.retMessage.BADLENGTH;
import static Validation.OldFiles.Validate_Username.retMessage.GOOD;
import static org.junit.Assert.assertNotNull;


/**
 * <h1>Validate_UsernameTest</h1>
 * Contains functions to sanitize and ensure username is in the correct format.
 *
 * Known Bugs:
 *     Lines 61-67 Mockito issues. Code may not be needed for testing, so they have been
 *     temporarily commented out.
 *
 * @author Oliver Yeudall
 * @version 1.0
 */

public class Validate_UsernameTest {
    Validate_Username usernameValidator1;
    XML_Reader xml_Reader;
    List<XML_Reader.Tags_To_Read> list;

    @Before
    public void setUp() throws Exception {
        usernameValidator1 = new Validate_Username();

        xml_Reader = new Account_Reader();
        list = new LinkedList<>();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isAccountNameValid() {
        list.add(XML_Reader.Tags_To_Read.Account_Name);
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("empty_file.xml");
        XmlPullParser xmlPullParser = Mockito.mock(XmlPullParser.class);
        Mockito.when(xmlPullParser.getName()).thenReturn("a");
        /*Mockito.when(xmlPullParser.next()).thenReturn(XmlPullParser.END_DOCUMENT);
        try {
            Map<String, String> map = xml_Reader.Read_File(xmlPullParser, list);
            assertNotNull(map);
        } catch (XML_Reader_Exception e) {
            e.printStackTrace();
        }*/


        Assert.assertEquals(GOOD, usernameValidator1.isUsernameValid("myusername"));
        Assert.assertEquals(BADLENGTH, usernameValidator1.isUsernameValid("myuser"));
        Assert.assertEquals(BADCHAR, usernameValidator1.isUsernameValid("my<pass"));
        //Assert.assertEquals(BADCODE, passwordValidator2.isUsernameValid("my<pass"));
        //Assert.assertEquals(BADOTHER, passwordValidator2.isUsernameValid("my<pass"));
    }
}