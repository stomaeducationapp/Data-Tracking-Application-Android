package capstonegroup2.dataapp.Account_Modification;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Account_Information.Account_Data;
import XML.Account_Reader;
import XML.XML_Reader;
import capstonegroup2.dataapp.R;

public class Account_Information_Main extends AppCompatActivity {
    private Account_Data account_data;
    private XML_Reader xml_reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xml_reader = new Account_Reader();
        account_data = new Account_Data();
        Read_All_Account_Data();
        final Button name_Btn = findViewById(R.id.name_Btn);
        name_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                Account_Information_Name_Fragment account_information_name_fragment = Account_Information_Name_Fragment.newInstance(account_data.getAccount_Name());
                account_information_name_fragment.show(fm, "fragment_account__information__name_");
// TODO: 10-Oct-18 will need get information back as true and false maybe 

                //read_Specified_Account_Data();
            }
        });
        final Button password_Btn = findViewById(R.id.password_Btn);
        password_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
// TODO: 10-Oct-18 will need to call validate pw 

                // your handler code here

            }
        });


        final Button notifications_Btn = findViewById(R.id.notifications_Btn);
        notifications_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                //read_Specified_Account_Data();
            }
        });
        final Button export_Btn = findViewById(R.id.export_Btn);
        export_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                //read_Specified_Account_Data(XML_Reader.Tags_To_Read.Export_Settings); // TODO: 10-Oct-18 This needs to be updated when export settings is added to reader and writer
            }
        });
        final Button gamification_Btn = findViewById(R.id.gamification_Btn);
        gamification_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

                //read_Specified_Account_Data();
            }
        });
        final Button delete_Btn = findViewById(R.id.delete_Btn);
        delete_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here

            }
        });
        final Button return_Btn = findViewById(R.id.return_Btn);
        return_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
            }
        });
        setContentView(R.layout.activity_account_modification_main);
    }

    //Gamification, Notification, State, Name
    private void Read_All_Account_Data() {// TODO: 10-Oct-18 this will be changed as xml reader will take a file instead
        List<XML_Reader.Tags_To_Read> tag_List = new LinkedList<>();
        tag_List.add(XML_Reader.Tags_To_Read.Name);
        tag_List.add(XML_Reader.Tags_To_Read.Notification);
        tag_List.add(XML_Reader.Tags_To_Read.State);
        tag_List.add(XML_Reader.Tags_To_Read.Gamification);
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            factory.setNamespaceAware(false);
            Map<String, String> account_Values = xml_reader.Read_File(xpp, tag_List, account_data.getAccount_Name());
            if (!account_Values.isEmpty()) {
                account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.Name.toString()));
                account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.Notification.toString()));
                account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.State.toString()));
                account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.Gamification.toString()));
            }
        } catch (XmlPullParserException | XML.XML_Reader_Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Account_Information(String field, String value) {
        if (field.equals(XML_Reader.Tags_To_Read.Name.toString())) {
        } else if (field.equals(XML_Reader.Tags_To_Read.Notification.toString())) {
        } else if (field.equals(XML_Reader.Tags_To_Read.State.toString())) {
        } else if (field.equals(XML_Reader.Tags_To_Read.Gamification.toString())) {
        }
    }
}
