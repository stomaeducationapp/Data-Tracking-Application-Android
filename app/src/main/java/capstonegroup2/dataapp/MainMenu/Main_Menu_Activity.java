package capstonegroup2.dataapp.MainMenu;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import Factory.Factory;
import Observers.Time_Observer;
import capstonegroup2.dataapp.MainMenu.Fragments.Green_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Information_Change;
import capstonegroup2.dataapp.MainMenu.Fragments.Red_State_Fragment;
import capstonegroup2.dataapp.MainMenu.Fragments.Yellow_State_Fragment;
import capstonegroup2.dataapp.R;

/**
 *
 */
public class Main_Menu_Activity extends Activity implements Green_State_Fragment.Green_Fragment_Data_Listener, Yellow_State_Fragment.Yellow_Fragment_Data_Listener, Red_State_Fragment.Red_Fragment_Data_Listener {

    private static final String GREEN = "Green";
    private static final String YELLOW = "Yellow";
    private static final String RED = "Red";
    private static final String ACCOUNT_NAME = "Accountname";
    private static final String DAILY = "Daily";
    private static final String EXPORT = "Export";
    // TODO: 05-Nov-18 will need to catch the null point if information is wrong and then return to login screen
    private Account_Data_Container account_data_container;
    private boolean state_Invalid;
    private Factory factory;
    private Fragment active_Fragment; // TODO: 06-Nov-18 need to get the active fragment to call back to

    private boolean review_required;
    private boolean export_required;
    private Information_Change fragment_CallBack;
    private Thread daily;
    private Thread export;
    private Thread daily_Wait;
    private Thread export_Wait;
    private Object toast_Lock;
    private boolean lock;
    // Time_Observer export_Data_Obs;
    // Time_Observer daily_Review_Obs;


    /**
     * Override from Super method onCreate()
     * Instantiates all the buttons, values and observers used by the main menu.
     * Once successful it reads the account information from the accountinformation.xml from the account file. This
     * information is then used to generate the correct fragment and settings available.
     * There are two calls in this method that use new threads 'daily' and 'export' using .start90. these threads are
     * handled if still active with onDestroy().
     * <h1>Notes</h1>
     * There is currently commented out code that is there for when integration with other activities and the file
     * system.
     * <h1>Bugs and Future Functionality</h1>
     * The file system will need to be worked out at a later date.
     * Currently there is no gamification score handling as this functionality hasn't been designed and needs to be
     * added at a later date.
     *
     * @param savedInstanceState state for if application is paused
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__main__menu);


        state_Invalid = false;
        account_data_container = new Account_Data_Container();

       //THis is to get the current account name in case the user changes it in account_information package so the account file can be found.
        //Intent intent = this.getIntent();
        //account_data_container.setAccount_Name(intent.getStringExtra(ACCOUNT_NAME));
        factory = Factory.Get_Factory();
        review_required = false;
        export_required = false;
        toast_Lock = new Object();
        lock = false;
        // export_Data_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
        //daily_Review_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Daily_Review);

        /* *********** Uncomment when not demo and integration occurring and required
         Populate_Account_Data_Container();
         Export_Data_Check();
         Daily_Review_Check();
         Change_Account_State();
         if(review_required){
             if(account_data_container.getNotifications().equals(/*Need to get the correct values*)){
             //// TODO: 08-Nov-18 toast
                Review_Handler rh = new Review_Handler();
                daily = new Thread(rh);
                daily.start();
             }
         if(export_required){
             //check account notification settings and if passes call the fragment to tell the user
             if(account_data_container.getNotifications().equals(/*Need to get the correct values*)){
                 // TODO: 08-Nov-18 notify through toast

                 }
         *************************/
        //Demo code to allow for switching of states
        Button next_State_Demo = findViewById(R.id.Demo_Btn);
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
        Button notification_Demo = findViewById(R.id.Notification_Demo_Btn);
        notification_Demo.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Please Export Data when next connected to the internet", Toast.LENGTH_LONG).show();
            }
        });
        //Demo Code to populate information
        account_data_container.setState(GREEN);
        account_data_container.setAccount_Name("Bob");
        account_data_container.setGamification_Mode("Mode 1");
        Change_Account_State();
    }

    /**
     * Override of onDestory(). Is used to interrupt any threads that are currently alive so no leaks can occur.
     */
    @Override
    public void onDestroy() {
        if (daily.isAlive()) {
            daily.interrupt();
        }
        if (export.isAlive()) {
            export.interrupt();
        }
        if (daily_Wait.isAlive()) {
            daily_Wait.interrupt();
        }
        if (export_Wait.isAlive()) {
            export_Wait.interrupt();
        }
        super.onDestroy();
    }

    /**
     * This private method functionality is to populate the account information container object from the xml file on
     * creation of the activity. This information is gotten from the xml reader and put into the container.
     */
    private void Populate_Account_Data_Container() {
        //XML reader.
        // TODO: 07-Nov-18  Get Account information file from context
        //Need to get all fields as any of them could have changed, then check against current values stored
                /* Gamification, Notification, State, Name, Export_Settings
                tags.add(XML_Reader.Tags_To_Read.Gamification);
                tags.add(XML_Reader.Tags_To_Read.Notification);
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
                            }
                        }else if(key.equals(XML_Reader.Tags_To_Read.Export_Settings.toString()){
                                account_data_container.setExport_Settings(value);
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

    /**
     * This private methods functionality is to change the state fragment based on the value stored in
     * account_data_container. If the value isn't any of the expected values the, first time the value is re-pulled from
     * the xml file. If the check fails again the value is set to GREEN to allow the app to continue functioning.
     * The old fragment is disposed of
     * <h1>Note</h1>
     * Warning This method can recursively call itself. Should only be n = 2, as first time it tries to get the state
     * again and second time will overwrite it to green to fix itself. If an outside source changes this at the same an
     * infinite loop could occur. In current setup this cant happen.
     */
    private void Change_Account_State() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        String account_State = account_data_container.getState();
        // TODO: 07-Nov-18 will need to call the custom new instance and give the information
        switch (account_State) {
            case GREEN:
                active_Fragment = Green_State_Fragment.newInstance(account_data_container.getGamification_Mode(), review_required, account_data_container.getAccount_Name());
                fragment_CallBack = (Information_Change) active_Fragment;
                ft.replace(R.id.State_Container, active_Fragment);
                ft.commit();
                break;
            case YELLOW:
                active_Fragment = Yellow_State_Fragment.newInstance(account_data_container.getGamification_Mode(), review_required, account_data_container.getAccount_Name());
                fragment_CallBack = (Information_Change) active_Fragment;
                ft.replace(R.id.State_Container, active_Fragment);
                ft.commit();
                break;
            case RED:
                active_Fragment = Red_State_Fragment.newInstance();
                fragment_CallBack = null;
                ft.replace(R.id.State_Container, active_Fragment);
                ft.commit();
                break;
            default:
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

    /**
     * This private methods functionality is to check if the current date is 7 days after the last export occurred. If
     * this is true the boolean value is set to true. THis method handles change of years, months, and leap years.
     */
    private void Export_Data_Check() {
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
                day_Last = 31 - 6;
            } else {
                if (current_Month % 2 != 0) {//30 days in previous Month
                    if (current_Month == 2) {//FEB
                        //Check Leap Year!!
                        if ((current_Year % 4 == 0) && (current_Year % 100 == 0)) {//leap year
                            day_Last = 29 - 6;
                        } else {
                            day_Last = 28 - 6;
                        }
                    } else {
                        day_Last = 30 - 6;
                    }
                } else {//31 days in previous Month
                    day_Last = 31 - 6;
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
            int time_Left = 0;
            Waiting_Thread waiting_thread = new Waiting_Thread(time_Left, EXPORT);
            daily_Wait = new Thread(waiting_thread);
            daily_Wait.start();
        }
    }

    /**
     * This private methods functionality is to check if the current date is 1 days after the last daily review
     * occurred. If this is true the boolean value is set to true. Ths method handles change of years, months, and leap
     * years. If time is still left a new thread is created and waiting for the alotted time, unless interrupted due to
     * destruction of activity.
     */
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
            // TODO: 08-Nov-18 get time remaining
            int time_Left = 0;
            Waiting_Thread waiting_thread = new Waiting_Thread(time_Left, DAILY);
            daily_Wait = new Thread(waiting_thread);
            daily_Wait.start();

        }
    }

    //Green and Yellow are the same currently, but in the future may need different functionality so are separated into methods

    /**
     * This method is the concrete implementation of Green_Fragment_Data_Listener to allow the fragment to communicate
     * back to the main activity it is within. THis method useds a switch layout to select which values need to be
     * updated within the account information container. If any information doesn't change it is not overwritten
     * A lot of the functionality is commented out as it required integration to allow to work.
     *
     * @param field enum value on what has been changed from the fragment button press
     */
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
                tags.add(XML_Reader.Tags_To_Read.Export_Settings);
                xml_reader = factory.Make_XML_Reader(ACCOUNT)
                Map<String, String> information = xml_reader.Read_File(tags, account_Name);
                for (Map.Entry<String, String> entry : information.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if(!value.equals("")){
                        if(key.equals(XML_Reader.Tags_To_Read.Notification.toString()){
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

    /**
     * This method is the concrete implementation of export for yellow and green fragment interfaces to allow the export button to call the main menu activity to start the export thread.
     */
    @Override
    public void export() {
        //Export_Handler eh = new Export_Handler();
        //                export = new Thread(eh);
        //                export.start();
    }

    /**
     * This method is the concrete implementation of Red_Fragment_Data_Listener to allow the fragment to communicate.
     * Currently the only required functionality required is resetting the state to Green. This is then put into a
     * separate medical entry as it is an important and signification event. This does break some of the protocol as
     * there is no medical calculations for the state but is to allow for NFR from the client to be able to reset it
     * in case it isn't correct or they have seeked help and back to good health
     *
     * @param field enum value on what has been changed from the fragment button press
     */
    @Override
    public void onChangedData(Red_State_Fragment.Fields field) {
//Currently will only change state from Red to Green, Will need XML writer and then just update the fields here
        //Get the medical information file and add new entry with only state = green and let everything else be default
        //xml writer ->set State for account and medical information.xml files this is a breach in protocol due to needing to reset everything
        account_data_container.setState(GREEN);
        Change_Account_State();
    }

    /**
     * This method is the concrete implementation of Yellow_Fragment_Data_Listener to allow the fragment to communicate
     * back to the main activity it is within. THis method useds a switch layout to select which values need to be
     * updated within the account information container. If any information doesn't change it is not overwritten
     * A lot of the functionality is commented out as it required integration to allow to work.
     *
     * @param field enum value on what has been changed from the fragment button press
     */
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
                            if(key.equals(XML_Reader.Tags_To_Read.Notification.toString()){
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

    /**
     * This class private thread class is used to generate the new daily review so the GUI doesn't hang while this is happening.
     * Note: Doesn't follow current notification settings
     */
    private class Review_Handler implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (toast_Lock) {
                    if (lock) {
                        toast_Lock.wait();

                    }
                    toast_Lock = true;
                    Toast.makeText(getApplicationContext(), "Generating review of previous 24 hours please wait until completed to enter in new medical data", Toast.LENGTH_LONG).show();
                    Thread.sleep(Toast.LENGTH_LONG);
                    toast_Lock = false;
                    toast_Lock.notify();
                }
                // TODO: 08-Nov-18 get context for medical file, review file, and account file
                //Files are set to null for now. NOTE: do not uncomment daily_review_Obs.Notify() if they are null as an error will occur every time
                File med = null;
                File rev = null;
                File acc = null;
                Map<Time_Observer.Files, File> files = new HashMap<>();
                files.put(Time_Observer.Files.Account, acc);
                files.put(Time_Observer.Files.Review, rev);
                files.put(Time_Observer.Files.Medical, med);
                //daily_Review_Obs.Notify(files);
            } catch (InterruptedException ex) {
                review_required = true;
            }
        }
    }
    /**
     * This class private thread class is used to export a users medical data so the GUI doesn't hang while this is happening.
     * Note: Doesn't follow current notification settings
     */
    private class Export_Handler implements Runnable {
        @Override
        public void run() {
            try {

                synchronized (toast_Lock) {
                    if (lock) {
                        toast_Lock.wait();

                    }
                    toast_Lock = true;
                    Toast.makeText(getApplicationContext(), "Currently Exporting Data please wait until completed to enter in new medical data", Toast.LENGTH_LONG).show();
                    Thread.sleep(Toast.LENGTH_LONG);
                    toast_Lock = false;
                    toast_Lock.notify();
                }
                // TODO: 08-Nov-18 get context for medical file, review file, and account file
                //Files are set to null for now. NOTE: do not uncomment daily_review_Obs.Notify() if they are null as an error will occur every time
                File med = null;
                File acc = null;
                Map<Time_Observer.Files, File> files = new HashMap<>();
                files.put(Time_Observer.Files.Account, acc);
                files.put(Time_Observer.Files.Medical, med);
                //export_Data_Obs.Notify(files);
            } catch (InterruptedException ex) {
                export_required = true;
            }
        }
    }

    /**
     * This class private thread is to create a thread to sleep until a daily review of export is required and the appropriate action occurs.
     * Note: Doesn't follow current notification settings
     */
    private class Waiting_Thread implements Runnable {
        int time;
        String type;

        public Waiting_Thread(int time, String type) {
            this.time = time;
            this.type = type;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(time);
                switch (type) {
                    case DAILY:
                        Review_Handler rh = new Review_Handler();
                        daily = new Thread(rh);
                        daily.start();
                        break;
                    case EXPORT:
                        Toast.makeText(getApplicationContext(), "Please Export Data when next connected to the internet", Toast.LENGTH_LONG).show();
                        break;
                }
            } catch (InterruptedException e) {
                review_required = false;
            }
        }
    }
}
