package XML;

import android.util.Log;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class Account_Reader_unit_Test {

    @Test
    public void read_File() throws IOException {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("test.txt");
        BufferedReader rd = new BufferedReader(new InputStreamReader(is), 4096);
        String line;
        StringBuilder sb =  new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();


        String contentOfMyInputStream = sb.toString();
        System.out.println(contentOfMyInputStream);
        assertEquals("reader", contentOfMyInputStream);
    }
}