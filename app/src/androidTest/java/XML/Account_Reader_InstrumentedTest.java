package XML;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Account_Reader_InstrumentedTest {
    private static boolean setupDone = false;
    Context context;

    @Before
    public void setUp() {
        if (setupDone) {
            return;
        } else {
            context = InstrumentationRegistry.getTargetContext();
        }
    }

    @Test
    public void Test_Context() {
        assertEquals("capstonegroup2.dataapp", context.getPackageName());
    }


    @Test
    public void File_Stream_Check() {

        try {
            FileInputStream fs = context.openFileInput("valid_account_data.xml");
            assertNotNull(fs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void Empty_File_Pass() {
        try {
            FileInputStream fs = context.openFileInput("valid_account_data.xml");
            XML_Reader xml_reader = new Account_Reader();
            List<XML_Reader.Tags_To_Read> l = new LinkedList<>();
            l.add(XML_Reader.Tags_To_Read.Account_Name);
            Map<String, String> test = xml_reader.Read_File(fs, l);
            assertNotNull(test);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XML_Reader_Exception e) {
            e.printStackTrace();
        }
    }
}
