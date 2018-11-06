package capstonegroup2.dataapp.MainMenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import Factory.Factory;
import capstonegroup2.dataapp.MainMenu.Fragments.Green_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Red_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Yellow_State_Fragment;
import capstonegroup2.dataapp.R;
// TODO: 05-Nov-18 will need to save this activity state when going to another activity, except login screen
//https://stackoverflow.com/questions/151777/saving-android-activity-state-using-save-instance-state?rq=1

public class Main_Menu_Activity extends Activity implements Green_State_Fragment.Green_Fragment_Data_Listener, Yellow_State_Fragment.Yellow_Fragment_Data_Listener, Red_State_Fragment.Red_Fragment_Data_Listener {

    private static final String GREEN = "Green";
    private static final String YELLOW = "Yellow";
    private static final String RED = "Red";
    // TODO: 05-Nov-18 will need to catch the null point if information is wrong and then return to login screen
    private Account_Data_Container account_data_container;
    private boolean state_Invalid;
    private Factory factory;
    private Fragment active_Fragment; // TODO: 06-Nov-18 need to get the active fragment to call back to
    private Button next_State_Demo;
    private boolean review_required;
    private boolean export_required;

    // Time_Observer export_Data_Obs;
    // Time_Observer daily_Review_Obs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main__menu);
        state_Invalid = false;
        account_data_container = new Account_Data_Container();
        factory = Factory.Get_Factory();
        review_required = false;
        export_required = false;
        //export_Data_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
        //daily_Review_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Daily_Review);




        /* Uncomment when not demo
        Populate_Account_Data_Container();
        Export_Data_Check();
        Daily_Review_Check();
        Change_Account_State();
        if(review_required){
            // TODO: 07-Nov-18 async start review observer and then notify fragment to unlock buttons
        }
        if(!export_required){
            //check account notification settings and if passes call the fragment to tell the user
            if(account_data_container.getNotifications().equals(/*Need to get the correct values*?){
                // TODO: 07-Nov-18 push a notification to the user telling them time to export data 
        }
        */


        //Demo code to allow for switching of states
        next_State_Demo = findViewById(R.id.Demo_Btn);
        next_State_Demo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String account_State = account_data_container.getState();
                switch (account_State) {
                    case GREEN:
                        account_data_container.setState(YELLOW);
                        Change_Account_State();
                        break;
                    case YELLOW:
                        account_data_container.setState(RED);
                        Change_Account_State();
                        break;
                    case RED:
                        account_data_container.setState(GREEN);
                        Change_Account_State();
                        break;
                }
            }
        });


    }

    private void Populate_Account_Data_Container(){
        //XML reader.
        // TODO: 07-Nov-18  Get Account information file from context
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
                                account_data_container.setAccount_Name(value);
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Notification.toString()){
                                account_data_container.setNotifications(value);
                                // TODO: 06-Nov-18 will need to set the notification settings
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Export_Settings.toString()){
                                account_data_container.setExport_Settings(value);
                                // TODO: 06-Nov-18 will need to set the export settings
                        }else if(key.equals(XML_Reader.Tags_To_Read.Gamification.toString()){
                                account_data_container.setGamification_Mode(value);
                            }
                        }
                    }
                }
                */
    }

    //this will be used to select what fragment is loaded in
    //Warning This method can recursively call itself. Should only be n = 2, as first time it tries to get the state again and second time will overwrite it to green to fix itself
    private void Change_Account_State() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        String account_State = account_data_container.getState();
        // TODO: 07-Nov-18 will need to call the custom new instance and give the information
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
    private void Export_Data_Check() {
//get the date 7 days ago and then check if last export date is before that
        String yesterdays_Date = account_data_container.getLast_Daily_Review_Date();
        String[] date_info = yesterdays_Date.split("-");
        Calendar calender = Calendar.getInstance();
        int current_Day = calender.get(Calendar.DAY_OF_MONTH);
        int current_Month = calender.get(Calendar.MONTH) + 1;
        int current_Year = calender.get(Calendar.YEAR);
        int day_Last;
        int month_Last;
        int year_Last;
        if (current_Day <= 7) {//need to go back 1 month
            if (current_Month == 1) {
                month_Last = 12;
                year_Last = current_Year - 1;
                day_Last = 31-6;
            }else{
                if (current_Month % 2 != 0) {//30 days in previous Month
                    if (current_Month == 2) {//FEB
                        //Check Leap Year!!
                        if ((current_Year % 4 == 0) && (current_Year % 100 == 0)) {//leap year
                            day_Last = 29-6;
                        } else {
                            day_Last = 28-6;
                        }
                    } else {
                        day_Last = 30-6;
                    }
                } else {//31 days in previous Month
                    day_Last = 31-6;
                }
                month_Last = current_Month - 1; // After Day is sorted go back to previous Month in Calendar
                year_Last = current_Year;
            }
        } else {
            day_Last = current_Day - 7;
            month_Last = current_Month;
            year_Last = current_Year;
        }
        //check if done 1 or more days ago
        if (year_Last >= Integer.parseInt(date_info[3])) {
            if (month_Last >= Integer.parseInt(date_info[2])) {
                if (day_Last > Integer.parseInt(date_info[1])) {
                    export_required = true;
                }
            }
        } else {
            // TODO: 07-Nov-18 this needs to then check how long until that time is and have a callback in that long. See if system/context has a way to do that
        }

    }

    private void Daily_Review_Check() {
        String yesterdays_Date = account_data_container.getLast_Daily_Review_Date();
        String[] date_info = yesterdays_Date.split("-");
        Calendar calender = Calendar.getInstance();
        int current_Time = calender.get(Calendar.HOUR_OF_DAY);
        int current_Day = calender.get(Calendar.DAY_OF_MONTH);
        int current_Month = calender.get(Calendar.MONTH) + 1;
        int current_Year = calender.get(Calendar.YEAR);
        if (current_Time >= 9) {//is within valid time to generate review
            //check if done 1 or more days ago
            if (current_Year >= Integer.parseInt(date_info[3])) {
                if (current_Month >= Integer.parseInt(date_info[2])) {
                    if (current_Day > Integer.parseInt(date_info[1])) {
                        review_required = true;
                    }
                }
            }
        } else {
            // TODO: 07-Nov-18 this needs to then check how long until that time is and have a callback in that long. See if system/context has a way to do that 
        }
    }

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
                tags.add(XML_Reader.Tags_To_Read.Export_Notification);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.Export_Notification){
                new_Value = information.get(XML_Reader.Tags_To_Read.Export_Notification);
                    if(!new_Value.equals(""){
                        account_data_container.setLast_Export_Date(new_Value);
                        current_Fragment.updateInformation(Information_Change.Field.Export_Notification, value);
                    }
                }
                 */
                break;
            case daily_Review:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.Export_Notification);
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
                // TODO: 07-Nov-18  Get Account information file from context
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
                tags.add(XML_Reader.Tags_To_Read.Export_Notification);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                if(information.contains(XML_Reader.Tags_To_Read.Export_Notification){
                new_Value = information.get(XML_Reader.Tags_To_Read.Export_Notification);
                    if(!new_Value.equals(""){
                        account_data_container.setLast_Export_Date(new_Value);
                        current_Fragment.updateInformation(Information_Change.Field.Export_Notification, value);
                    }
                }
                 */
                break;
            case daily_Review:
                //Get Account information file from context
                /*
                tags.add(XML_Reader.Tags_To_Read.Export_Notification);
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
                 * If the account name has changed this needs to be given back to here somehow otherwise the account directory doesn't exist!!! will need to be rectified later.
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
