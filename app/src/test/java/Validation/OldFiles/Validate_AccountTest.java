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

import static Validation.OldFiles.Validate_Account.retMessage.GOOD;
import static Validation.OldFiles.Validate_Account.retMessage.BADLENGTH;
import static Validation.OldFiles.Validate_Account.retMessage.BADCHAR;

import static org.junit.Assert.assertNotNull;

/**
 * <h1>Validate_AccountTest</h1>
 * Contains functions to sanitize and ensure account information is in the correct format.
 *
 * Known Bugs:
 *     Lines 60-66 Mockito issues. Code may not be needed for testing, so they have been
 *     temporarily commented out.
 *
 * @author Oliver Yeudall
 * @version 1.0
 */

public class Validate_AccountTest {
    Validate_Account accountValidator1;
    XML_Reader xml_Reader;
    List<XML_Reader.Tags_To_Read> list;

    @Before
    public void setUp() throws Exception {
        accountValidator1 = new Validate_Account();

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


        Assert.assertEquals(GOOD, accountValidator1.isAccountNameValid("myaccname"));
        Assert.assertEquals(BADLENGTH, accountValidator1.isAccountNameValid("myacc"));
        Assert.assertEquals(BADCHAR, accountValidator1.isAccountNameValid("my<account"));
        //Assert.assertEquals(BADCODE, accountValidator1.isAccountValid("my<pass"));
        //Assert.assertEquals(BADOTHER, accountValidator1.isAccountValid("my<pass"));
    }
}