package capstonegroup2.dataapp.MainMenu.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import Factory.Factory;
import capstonegroup2.dataapp.MainMenu.News_Package.News_PlaceHolder;
import capstonegroup2.dataapp.R;

/**
 * When navigating from this page need to save the instance and reload it when returning, including the activity
 * <p>
 * // TODO: 04-Nov-18 will need to add checks to start the exporting/generating of data
 * <p>
 * work out a system to work out when th check for it even when the app is active,
 * will probably check when logged in and record a time that is checked after awhile
 * or have a back end thread sleep for that long.
 * <p>
 * <p>
 * Gamification data will need to be managed within the state as the activity doesn't know about it
 * <p>
 * Will probably need a gamification object to accept the gamification data and update itself from the fragment
 * There will need to be communication and reading/writing to file from this fragment for it
 */
public class Yellow_State_Fragment extends Fragment implements Information_Change {

    public enum Fields {export_Time, daily_Review, account_Information, state}

    private static final String GAME_MODE_ARG = "gamification_State";
    private static final String EXPORT_DONE_ARG = "export_Done";
    private static final String ACCOUNT_NAME_ARG = "Account_Name";
    private static final String MODE_1 = "Mode 1";
    private static final String MODE_2 = "Mode 2";
    private static final String MODE_3 = "Mode 3";
    //private static final String ACCOUNT_FILE = "Accounts";
    //private static final String PATH_SEPARATOR = System.getProperty("");
    /**
     * Name of the Medical file for each account
     */
    //private static final String MEDICAL_INFORMATION_FILE = "MedicalInformationFile.xml";
    /**
     * Name of the Review file for each account
     */
    //private static final String REVIEW_FILE_NAME = "ReviewInformationFile.xml";


    private String gamification_Mode;
    //private String account_name;
    private boolean daily_Review_Required;

    private Button Medical_Input_Btn;
    private Button Review_Btn;
    private Button Account_Information_Btn;
    private Button Challenges_Btn;
    private Button Export_Btn;
    Yellow_Fragment_Data_Listener listener;
    News_PlaceHolder news;
    Factory factory;
    // Time_Observer export_Data_Obs;
    // Time_Observer daily_Review_Obs;
    // Form_Change_Observer form_Switcher_Obs;

    public static Yellow_State_Fragment newInstance(String gamification_Mode, Boolean daily_Review, String account_Name) {
        Yellow_State_Fragment yellow_state_fragment = new Yellow_State_Fragment();
        Bundle args = new Bundle();
        args.putString(GAME_MODE_ARG, gamification_Mode);
        args.putString(ACCOUNT_NAME_ARG, account_Name);
        args.putBoolean(EXPORT_DONE_ARG, daily_Review);
        yellow_state_fragment.setArguments(args);
        return yellow_state_fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamification_Mode = getArguments().getString(GAME_MODE_ARG);
        //account_name = getArguments().getString(ACCOUNT_NAME_ARG);
        daily_Review_Required = getArguments().getBoolean(EXPORT_DONE_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listener = (Yellow_Fragment_Data_Listener) getActivity();

        View view = inflater.inflate(R.layout.yellow__state__fragment, container, false);
        //getting all the parts of the display to be used
        Medical_Input_Btn = view.findViewById(R.id.Medical_Input_Btn);
        Review_Btn = view.findViewById(R.id.Review_Btn);
        Account_Information_Btn = view.findViewById(R.id.Account_Information_Btn);
        Challenges_Btn = view.findViewById(R.id.Challenges_Btn);
        Export_Btn = view.findViewById(R.id.Export_Btn);
        factory = Factory.Get_Factory();
        //export_Data_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
        //daily_Review_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Daily_Review);
        //form_Switcher_Obs = factory.Make_Form_Change_Observer();
        Setup_Gamification();

        TextView news_Text = view.findViewById(R.id.News);
        news = new News_PlaceHolder();
        news_Text.setText(news.getMockNews());
        if (daily_Review_Required) {
            Medical_Input_Btn.setEnabled(false);
            Review_Btn.setEnabled(false);
        }
        return view;
    }

    private void Setup_Gamification() {
        if (gamification_Mode != null) {
            switch (gamification_Mode) {
                case MODE_1:
                    Mode_1_Creation();
                    break;
                case MODE_2:
                    Mode_2_Creation();
                    break;
                case MODE_3:
                    Mode_3_Creation();
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
    }


    //Setup both buttons for full gamification
    private void Mode_1_Creation() {
        Generic_Button_Creation();
        Challenges_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Challenges, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
    }

    //Disable Gamification and setup challenges. THe mode will be handled by challenges section
    private void Mode_2_Creation() {
        Generic_Button_Creation();
        Challenges_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Challenges, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
    }

    //Disable gamification and change challenges to activities
    private void Mode_3_Creation() {
        Generic_Button_Creation();
        Challenges_Btn.setText(R.string.Activities);
        Challenges_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Challenges, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
    }

    private void Generic_Button_Creation() {
        Medical_Input_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Medical_Data_Input, null);
                    listener.onChangedData(Fields.state);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
        Review_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Review, null);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
        Account_Information_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                    // TODO: 04-Nov-18 make an intent
                    form_Switcher_Obs.Change_Form(Form_Change_Observer.Activity_Control.Account_Information, null);
                    listener.onChangedData(Fields.account_Information);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
        Export_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                /*try {
                     Context context = view.getContext;
                 //This will need to change as it is a rough draft of what should happen
                 File medical_Data = new File(context.getFilesDir(), ACCOUNT_FILE + FILE_SEPARATOR + account_Name + MEDICAL_INFORMATION_FILE)
                 export_Data_Obs.notify(medical_Data);
                    // TODO: 05-Nov-18 get the current time to send
                    listener.onChangedData(Fields.export_Time);
                }catch(Invalid_Enum_Exception ex){
                    //This should never happen and will be a run time error
                    // TODO: 04-Nov-18 look up how to display a big warning that the code itself is bad
                }*/
            }
        });
    }

    @Override
    public void update(Field field, String value) {
        switch (field) {
            case Name:
                break;
            case Gamification:
                break;
            case Last_Daily_Review_Date:
                daily_Review_Required = false;
                Medical_Input_Btn.setEnabled(true);
                Review_Btn.setEnabled(true);
                break;
        }
    }

    public interface Yellow_Fragment_Data_Listener {
        void onChangedData(Fields field);

    }

}
