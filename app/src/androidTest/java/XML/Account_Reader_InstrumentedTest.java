package XML;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

        Log.d("MyApp","starting");
            String[] sl = context.fileList();

            for(String a : sl){
                Log.d("MyApp",a);
            }

    }

    @Test
    public void File_Stream_Check2() {
        setUp();
        try {
            Log.d("MyApp","I am here1");
            InputStream fs = context.getAssets().open("test.txt");
            assertNotNull(fs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void Empty_File_Pass() {
        try {
            FileInputStream fs = context.openFileInput("test_file_one.xml");
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

    @Test
    public void Valid_Account_Name() {
        //Setup Map that should occur
        Map<String, String> valid_Values = new HashMap<>();
        valid_Values.put(XML_Reader.Tags_To_Read.Account_Name + "", "a");
        try {
            FileInputStream fs = context.openFileInput("account_file_data.xml");
            XML_Reader xml_reader = new Account_Reader();
            List<XML_Reader.Tags_To_Read> l = new LinkedList<>();
            l.add(XML_Reader.Tags_To_Read.Account_Name);
            Map<String, String> test = xml_reader.Read_File(fs, l);
            assertTrue(Maps_Are_Equal(test, valid_Values));
            Log.d("MyApp","I am here1");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XML_Reader_Exception e) {
            e.printStackTrace();
        }
    }

    //Private function for checking Maps
    private boolean Maps_Are_Equal(Map<String, String> m1, Map<String, String> m2) {
        boolean valid = m1.equals(m2);
        return valid;
    }
    @Test
    public void Invalid_Account_Name() {
        //Setup Map that should occur
        Map<String, String> valid_Values = new HashMap<>();
        valid_Values.put(XML_Reader.Tags_To_Read.Account_Name + "", "aa");
        try {
            FileInputStream fs = context.openFileInput("account_file_data.xml");
            XML_Reader xml_reader = new Account_Reader();
            List<XML_Reader.Tags_To_Read> l = new LinkedList<>();
            l.add(XML_Reader.Tags_To_Read.Account_Name);
            Map<String, String> test = xml_reader.Read_File(fs, l);
            assertTrue(Maps_Are_Equal(test, valid_Values));
            Log.d("MyApp","I am here2");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XML_Reader_Exception e) {
            e.printStackTrace();
        }
    }


}
