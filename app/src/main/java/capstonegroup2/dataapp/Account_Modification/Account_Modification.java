package capstonegroup2.dataapp.Account_Modification;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import XML.XML_Reader;
import capstonegroup2.dataapp.R;

/**
 * Changing of XML information files will be done in this activity and not the fragments, the fragments will check if
 * valid only
 */
public class Account_Modification extends Activity implements Account_Information_Name_Fragment.OnAccountFragmentInteractionListener, Account_Information_Password_Fragment.OnPasswordFragmentInteractionListener, Account_Information_Export_Settings_Fragment.OnExportFragmentInteractionListener,Account_Information_Notifications_Fragment.OnNotificationFragmentInteractionListener {


    private Account_Data account_data;
    private XML_Reader xml_reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__account__modification);
        //xml_reader = new Account_Reader();
        account_data = new Account_Data();


        /*Demo data for unit testing as cant pull from file*/
        account_data.setAccount_Name("bob");
        account_data.setNotification("Yes");

        Read_All_Account_Data();
        final Button name_Btn = findViewById(R.id.name_Btn);
        name_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.app.FragmentManager fm = getFragmentManager();
                Account_Information_Name_Fragment account_information_name_fragment = Account_Information_Name_Fragment.newInstance(account_data.getAccount_Name());
                //account_information_name_fragment.show(fm, "Account_Information_Name_Fragment"); //TODO FIX
            }
        });
            /*final Button password_Btn = findViewById(R.id.password_Btn);
            password_Btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    android.app.FragmentManager fm = getFragmentManager();
                    Account_Information_Password_Fragment acount_information_password_fragment = Account_Information_Password_Fragment.newInstance();
                    account_information_name_fragment.show(fm,"Account_Information_Password_Fragment");
                }
            });*/


        final Button notifications_Btn = findViewById(R.id.notifications_Btn);
        notifications_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.app.FragmentManager fm = getFragmentManager();
                Account_Information_Notifications_Fragment account_information_name_fragment = Account_Information_Notifications_Fragment.newInstance(account_data.getNotification());
                account_information_name_fragment.show(fm, "account_information_name_fragment");
            }
        });

        final Button export_Btn = findViewById(R.id.export_Btn);
        export_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                android.app.FragmentManager fm = getFragmentManager();
                Account_Information_Export_Settings_Fragment account_information_export_settings_fragment = Account_Information_Export_Settings_Fragment.newInstance();
                account_information_export_settings_fragment.show(fm, "account_information_name_fragment");
            }
        });
            /*final Button gamification_Btn = findViewById(R.id.gamification_Btn);
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
            });*/
    }

    //Gamification, Notification, State, Name
    private void Read_All_Account_Data() {// TODO: 10-Oct-18 this will be changed as xml reader will take a file instead
            /*List<XML_Reader.Tags_To_Read> tag_List = new LinkedList<>();
            tag_List.add(XML_Reader.Tags_To_Read.Name);
            tag_List.add(XML_Reader.Tags_To_Read.Notification);
            tag_List.add(XML_Reader.Tags_To_Read.State);
            tag_List.add(XML_Reader.Tags_To_Read.Gamification);
            XmlPullParserFactory factory;
            try {
                factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                factory.setNamespaceAware(false);
                //File file = new File("text.txt");
                Map<String, String> account_Values = xml_reader.Read_File(null, tag_List, account_data.getAccount_Name());
                if (!account_Values.isEmpty()) {
                    account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.Name.toString()));
                    account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.Notification.toString()));
                    account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.State.toString()));
                    account_data.setAccount_Name(account_Values.get(XML_Reader.Tags_To_Read.Gamification.toString()));
                }
            } catch (XmlPullParserException | XML.XML_Reader_Exception e) {
                e.printStackTrace();
            }*/
    }

    private void update_Account_Information(String field, String value) {
        if (field.equals(XML_Reader.Tags_To_Read.Name.toString())) {
            account_data.setAccount_Name(value);
        } else if (field.equals(XML_Reader.Tags_To_Read.Notification.toString())) {
            account_data.setNotification(value);
        } else if (field.equals(XML_Reader.Tags_To_Read.Export_Settings.toString())) {
            account_data.setExport_Settings(value);
        } else if (field.equals(XML_Reader.Tags_To_Read.Gamification.toString())) {
            account_data.setGamification(value);
        }
    }

    @Override
    public void onFinishAccountDialog(String account_Name) {
        if (!account_Name.equals(account_data.getAccount_Name())) {
            //update_Account_Information(XML_Reader.Tags_To_Read.Name.toString(), account_Name);
            account_data.setAccount_Name(account_Name);
        }
    }

    @Override
    public void onFinishPasswordDialog(String password) {
        //update_Account_Information(XML_Reader.Tags_To_Read.Password.toString(), password);
    }

    @Override
    public void onFinishExportDialog(String export) {
        if (!export.equals(account_data.getExport_Settings())) {
            //update_Account_Information(XML_Reader.Tags_To_Read.Export_Settings.toString(), export);
            account_data.setExport_Settings(export);
        }
    }

    @Override
    public void onFinishNotificationDialog(String notifications) {
        if (!notifications.equals(account_data.getNotification())) {
            //update_Account_Information(XML_Reader.Tags_To_Read.Notification.toString(), notifications);
            account_data.setNotification(notifications);
        }
    }
}


