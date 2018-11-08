package capstonegroup2.dataapp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import XML.Account_Reader;
import XML.XML_Reader;
import XML.XML_Reader_Exception;

import static capstonegroup2.dataapp.Validate_Answer.retMessage.BADCHAR;
import static capstonegroup2.dataapp.Validate_Answer.retMessage.BADLENGTH;
import static capstonegroup2.dataapp.Validate_Answer.retMessage.GOOD;
import static org.junit.Assert.assertNotNull;


/**
 * <h1>Validate_AnswerTest</h1>
 * Contains functions to sanitize and ensure username is in the correct format.
 * @author Oliver Yeudall
 * @version 1.0
 */

public class Validate_AnswerTest {
    Validate_Answer answerValidator1;
    XML_Reader xml_Reader;
    List<XML_Reader.Tags_To_Read> list;

    @Before
    public void setUp() throws Exception {
        answerValidator1 = new Validate_Answer();

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
        Mockito.when(xmlPullParser.next()).thenReturn(XmlPullParser.END_DOCUMENT);
        try {
            Map<String, String> map = xml_Reader.Read_File(xmlPullParser, list);
            assertNotNull(map);
        } catch (XML_Reader_Exception e) {
            e.printStackTrace();
        }


        Assert.assertEquals(GOOD, answerValidator1.isAnswerValid("myusername"));
        Assert.assertEquals(BADLENGTH, answerValidator1.isAnswerValid("myuser"));
        Assert.assertEquals(BADCHAR, answerValidator1.isAnswerValid("my<pass"));
        //Assert.assertEquals(BADCODE, answerValidator1.isAnswerValid("my<pass"));
        //Assert.assertEquals(BADOTHER, answerValidator1.isAnswerValid("my<pass"));
    }
}