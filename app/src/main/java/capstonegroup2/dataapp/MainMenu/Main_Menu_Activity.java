package capstonegroup2.dataapp.MainMenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import Factory.Factory;
import capstonegroup2.dataapp.MainMenu.Fragments.Green_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Red_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Yellow_State_Fragment;
import capstonegroup2.dataapp.R;
// TODO: 05-Nov-18 will need to save this activity state when going to another activity, except login screen
//https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state?rq=1

/**
 * KNOW BUGS OF THIS SECTION
 * IF you logged in during the switch over to another 24hour time, -> 9:00am, it will not disable the information and no
 * auto start the daily review generation.
 * will need to have a sleeping thread or something like that for it. 5-Nov-18
 * During Red state the exporting and creating of daily review doesn't occur. this could be done in the background as a
 * future functionality if decided
 */
public class Main_Menu_Activity extends Activity implements Green_State_Fragment.Green_Fragment_Data_Listener, Yellow_State_Fragment.Yellow_Fragment_Data_Listener, Red_State_Fragment.Red_Fragment_Data_Listener {

    private static final String GREEN = "Green";
    private static final String YELLOW = "Yellow";
    private static final String RED = "Red";
    // TODO: 05-Nov-18 will need to catch the null point if information is wrong and then return to login screen
    private Account_Data_Container account_data_container;
    private boolean state_Invalid;
    private Factory factory;
    private Fragment active_Fragment; // TODO: 06-Nov-18 need to get the active fragment to call back to
    // Time_Observer export_Data_Obs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        state_Invalid = false;
        account_data_container = new Account_Data_Container();
        factory = Factory.Get_Factory();
        // Will need to populate the container from XML
        /*Xml reader get all account information*/
        //export_Data_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);

        // TODO: 06-Nov-18 check the current time vs recorded times for export and daily, behave as required
        // TODO: 06-Nov-18 there will need to be a background thread sleeping and checking it as required
        Export_Data_Check();
        Daily_Review_Check();
        Change_Account_State();
    }

    //this will be used to select what fragment is loaded in
    //Warning This method can recursively call itself. Should only be n = 2, as first time it tries to get the state again and second time will overwrite it to green to fix itself
    private void Change_Account_State() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        String account_State = account_data_container.getState();
        switch (account_State) {
            case GREEN:
                ft.replace(R.id.State_Container, new Green_State_Fragment());
                ft.commit();
                break;
            case YELLOW:
                ft.replace(R.id.State_Container, new Yellow_State_Fragment());
                ft.commit();
                break;
            case RED:
                ft.replace(R.id.State_Container, new Red_State_Fragment());
                ft.commit();
                break;
            default:
                //need to re-pull from account information on device as maybe corrupted/invalid.
                if (!state_Invalid) {
                    state_Invalid = true;
                    //String temp = XML reader get state

                } else {
                    //will reset it to green
                    //xml writer state = green;
                    account_data_container.setState(GREEN);
                }
                Change_Account_State();
                break;
        }
    }

//Both these functions will need to update the fragment to enable buttons
    private void Export_Data_Check(){}

    private void Daily_Review_Check(){}

    /*
    These onChangedData() methods will need to be updated when integration happens at a future date, as a lot of it relies on system and xml reader
     */

    //Green and Yellow are the same currently, but future may need different functionality
    @Override
    public void onChangedData(Green_State_Fragment.Fields field) {
        switch (field) {
            case export_Time:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.Last_Export_Date);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.Last_Export_Date){
                new_Value = information.get(XML_Reader.Tags_To_Read.Last_Export_Date);
                    if(!new_Value.equals(""){
                        account_data_container.setLast_Export_Date(new_Value);
                        current_Fragment.updateInformation(Information_Change.Field.Last_Export_Date, value);
                    }
                }
                 */
                break;
            case daily_Review:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.Last_Export_Date);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.Last_Daily_Review_Date){
                new_Value = information.get(XML_Reader.Tags_To_Read.Last_Daily_Review_Date);
                    if(!new_Value.equals(""){
                        account_data_container.setLast_Daily_Review_Date(new_Value);
                        current_Fragment.updateInformation(Information_Change.Field.Last_Daily_Review_Date, value);
                    }
                }
                 */
                break;
            case account_Information:
                /*
                 * NOTE
                 * If the account name has changed this needs to be given back to here somehow otherwise the account directory doesn't exist!!!
                 */
                //Get Account information file from context
                //Need to get all fields as any of them could have changed, then check against current values stored
                /* Gamification, Notification, State, Name, Export_Settings
                tags.add(XML_Reader.Tags_To_Read.Gamification);
                tags.add(XML_Reader.Tags_To_Read.Notification);
                tags.add(XML_Reader.Tags_To_Read.Name);
                tags.add(XML_Reader.Tags_To_Read.Export_Settings);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                for (Map.Entry<String, String> entry : information.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if(!value.equals("")){
                        if(key.equals(XML_Reader.Tags_To_Read.Name.toString()){
                            if(!account_data_container.getAccount_Name().equals(value)){
                                account_data_container.setAccount_Name(value);
                                current_Fragment.updateInformation(Information_Change.Field.Name, value);
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Notification.toString()){
                            if(!account_data_container.getNotifications().equals(value)){
                                account_data_container.setNotifications(value);
                                // TODO: 06-Nov-18 will need to update the notification settings
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Export_Settings.toString()){
                            if(!account_data_container.getExport_Settings().equals(value)){
                                account_data_container.setExport_Settings(value);
                                // TODO: 06-Nov-18 will need to update the export settings
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Gamification.toString()){
                            if(!account_data_container.getGamification_Mode().equals(value)){
                                account_data_container.setGamification_Mode(value);
                                current_Fragment.updateInformation(Information_Change.Field.Gamification, value);
                            }
                        }
                    }
                }
                */
                break;
            case state:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.State);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.State){
                    new_Value = information.get(XML_Reader.Tags_To_Read.State);
                    if(!new_Value.equals( account_data_container.getState()){ //state has changed!
                        account_data_container.setState(new_Value);
                        Change_Account_State()
                    }
                }
                 */
                break;
        }
    }

    @Override
    public void onChangedData(Red_State_Fragment.Fields field) {
//Currently will only change state from Red to Green, Will need XML writer and then just update the fields here
        //Get the medical information file and add new entry with only state = green and let everything else be default
        //xml writer ->set State for account and medical information.xml files this is a breach in protocol due to needing to reset everything
        account_data_container.setState(GREEN);
        Change_Account_State();
    }


    @Override
    public void onChangedData(Yellow_State_Fragment.Fields field) {
        //XML_Reader xml_reader;
        //List<XML_Reader.Tags_To_Read> tags = new LinkedList<>();
        //String new_Value;
        switch (field) {
            case export_Time:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.Last_Export_Date);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.Last_Export_Date){
                new_Value = information.get(XML_Reader.Tags_To_Read.Last_Export_Date);
                    if(!new_Value.equals(""){
                        account_data_container.setLast_Export_Date(new_Value);
                        current_Fragment.updateInformation(Information_Change.Field.Last_Export_Date, value);
                    }
                }
                 */
                break;
            case daily_Review:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.Last_Export_Date);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.Last_Daily_Review_Date){
                new_Value = information.get(XML_Reader.Tags_To_Read.Last_Daily_Review_Date);
                    if(!new_Value.equals(""){
                        account_data_container.setLast_Daily_Review_Date(new_Value);
                        current_Fragment.updateInformation(Information_Change.Field.Last_Daily_Review_Date, value);
                    }
                }
                 */
            case account_Information:
                /*
                 * NOTE
                 * If the account name has changed this needs to be given back to here somehow otherwise the account directory doesn't exist!!!
                 */
                //Get Account information file from context
                //Need to get all fields as any of them could have changed, then check against current values stored
                /* Gamification, Notification, State, Name, Export_Settings
                tags.add(XML_Reader.Tags_To_Read.Gamification);
                tags.add(XML_Reader.Tags_To_Read.Notification);
                tags.add(XML_Reader.Tags_To_Read.Name);
                tags.add(XML_Reader.Tags_To_Read.Export_Settings);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                for (Map.Entry<String, String> entry : information.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if(!value.equals("")){
                        if(key.equals(XML_Reader.Tags_To_Read.Name.toString()){
                            if(!account_data_container.getAccount_Name().equals(value)){
                                account_data_container.setAccount_Name(value);
                                current_Fragment.updateInformation(Information_Change.Field.Name, value);
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Notification.toString()){
                            if(!account_data_container.getNotifications().equals(value)){
                                account_data_container.setNotifications(value);
                                // TODO: 06-Nov-18 will need to update the notification settings
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Export_Settings.toString()){
                            if(!account_data_container.getExport_Settings().equals(value)){
                                account_data_container.setExport_Settings(value);
                                // TODO: 06-Nov-18 will need to update the export settings, not sure how
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Gamification.toString()){
                            if(!account_data_container.getGamification_Mode().equals(value)){
                                account_data_container.setGamification_Mode(value);
                                current_Fragment.updateInformation(Information_Change.Field.Gamification, value);
                            }
                        }
                    }
                }
                */
                break;
            case state:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.State);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.State){
                    new_Value = information.get(XML_Reader.Tags_To_Read.State);
                    if(!new_Value.equals( account_data_container.getState()){ //state has changed!
                        account_data_container.setState(new_Value);
                        Change_Account_State()
                    }
                }
                 */
                break;
        }
    }
}
