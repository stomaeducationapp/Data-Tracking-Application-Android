package capstonegroup2.dataapp.MainMenu.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import Factory.Factory;
import Observers.Form_Change_Observer;
import Observers.Invalid_Enum_Exception;
import Observers.Time_Observer;
import capstonegroup2.dataapp.MainMenu.News_Package.News_PlaceHolder;
import capstonegroup2.dataapp.R;


/**
 * When navigating from this page need to save the instance and reload it when returning, including the activity
 */
// TODO: 04-Nov-18 will need to add checks to start the exporting/generating of data
    /*
    work out a system to work out when th check for it even when the app is active,
    will probably check when logged in and record a time that is checked after awhile
    or have a back end thread sleep for that long.
     */
public class Green_State_Fragment extends Fragment {
    private static final String GAME_MODE_ARG = "gamification_State";
    private static final String EXPORT_DONE_ARG = "export_Done";
    private static final String MODE_1 = "Mode 1";
    private static final String MODE_2 = "Mode 2";
    private static final String MODE_3 = "Mode 3";
    private String gamification_Mode;
    private boolean daily_Review;

    private Button Medical_Input_Btn;
    private Button Review_Btn;
    private Button Account_Information_Btn;
    private Button Gamification_Btn;
    private Button Challenges_Btn;
    private Button Export_Btn;

    Green_Fragment_Data_Listener listener;
    News_PlaceHolder news;
    Factory factory;

    Time_Observer export_Data_Obs;
    Time_Observer daily_Review_Obs;
    Form_Change_Observer form_Switcher_Obs;

    public enum Fields {state}

    public static Green_State_Fragment newInstance(String gamification_Mode/*, List<> gamification_Data (placeholder for information when gamification functionality is created*/, Boolean daily_Review) {
        Green_State_Fragment green_state_fragment = new Green_State_Fragment();
        Bundle args = new Bundle();
        args.putString(GAME_MODE_ARG, gamification_Mode);
        args.putBoolean(EXPORT_DONE_ARG, daily_Review);
        green_state_fragment.setArguments(args);
        return green_state_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamification_Mode = getArguments().getString(GAME_MODE_ARG);
        daily_Review = getArguments().getBoolean(EXPORT_DONE_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listener = (Green_Fragment_Data_Listener) getActivity();

        View view = inflater.inflate(R.layout.green__state__fragment, container, false);
        //getting all the parts of the display to be used
        Medical_Input_Btn = view.findViewById(R.id.Medical_Input_Btn);
        Review_Btn = view.findViewById(R.id.Review_Btn);
        Account_Information_Btn = view.findViewById(R.id.Account_Information_Btn);
        Gamification_Btn = view.findViewById(R.id.Gamification_Btn);
        Challenges_Btn = view.findViewById(R.id.Challenges_Btn);
        Export_Btn = view.findViewById(R.id.Export_Btn);
        factory = Factory.Get_Factory();
        export_Data_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
        daily_Review_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Daily_Review);
        form_Switcher_Obs = factory.Make_Form_Change_Observer();
        //Setup gamification and set names or disable some buttons depending on this mode
        // Will also determine if the avatar is showing or that section will show other things
        if (gamification_Mode != null) {
            switch (gamification_Mode) {
                case MODE_1:
                    Mode_1_Creation();
                    Setup_Avatar();
                    break;
                case MODE_2:
                    Mode_2_Creation();
                    Mode2_Extra_Info();
                    break;
                case MODE_3:
                    Mode_3_Creation();
                    Mode3_Extra_Info();
                    break;
                default:
                    //will need to probably load mode 1 or 3 and tell the user to set it in their settings
                    break;
            }
        } else {
            throw new NullPointerException("Gamification Mode == Null!!");
            //is an error will need to stop the creation of the fragment
            // This should never happen as the app logic is then corrupt or been modified wrongly!!!
        }
        //Add news information
        TextView news_Text = view.findViewById(R.id.News);
        news = new News_PlaceHolder();
        news_Text.setText(news.getMockNews());
        if (!daily_Review) {
            Medical_Input_Btn.setEnabled(false);
            Review_Btn.setEnabled(false);
            // TODO: 04-Nov-18 will need to spool a thread off and do the updating of daily review if not done yet
            //this will then enable the buttons again these 2 lines below may need to be put into the new thread most likely
            Medical_Input_Btn.setEnabled(true);
            Review_Btn.setEnabled(true);
        }
        return view;
    }

    //.setVisibility(View.GONE);
    //setVisibility(View.VISIBLE);

    //Setup both buttons for full gamification
    private void Mode_1_Creation() {
        Generic_Button_Creation();
        Gamification_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Gamification, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
        Challenges_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Challenges, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
    }

    //Disable Gamification and setup challenges. THe mode will be handled by challenges section
    private void Mode_2_Creation() {
        Generic_Button_Creation();
        Gamification_Btn.setVisibility(View.GONE);
        Challenges_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Challenges, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
    }
//Disable gamification and change challenges to activities
    private void Mode_3_Creation() {
        Generic_Button_Creation();
        Gamification_Btn.setVisibility(View.GONE);
        Challenges_Btn.setText(R.string.Activities);
        Challenges_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Challenges, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
    }

    private void Generic_Button_Creation() {
        Medical_Input_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Medical_Data_Input, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad 
                }
            }
        });
        Review_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Review, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
        Account_Information_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Account_Information, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
        Export_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Encrypt_and_Export, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }
            }
        });
    }

    private void Setup_Avatar(){
        //Will be hardcoded for now as no avatar available and no functionality
    }

    private void Mode2_Extra_Info(){
        //Filler for future what to put in the space the avatar is for game mode 1. Probably some tips or other information/fun stuff like a picture from St John or Kate
    }

    private void Mode3_Extra_Info(){
        //Filler for future what to put in the space the avatar is for game mode 1. Will be probably very basic information for a nice picture from St John or Kate
    }
    public interface Green_Fragment_Data_Listener {
        void onChangedData(Fields field, String value);

    }
}
