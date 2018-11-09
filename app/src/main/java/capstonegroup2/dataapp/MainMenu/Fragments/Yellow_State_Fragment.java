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
 * The Yellow_State_Fragment is used to contain all the information and functionality to show to the user on the main
 * activity, through dynamic fragments within it. This fragment is used when the users health is detected to be at a
 * unhealthy and possibly unstable level allowing them limited access to features and functionality of the application.
 * <h1>Notes</h1>
 * There is currently commented out code that is there for when integration with other activities and the file
 * system.
 * Will probably need a gamification object to accept the gamification data and update itself from the fragment
 * There is a section in the GUI that currently has placeholder information, bottom right instead of an avatar. This
 * will need to be filled with information at a later date from the client.
 */
public class Yellow_State_Fragment extends Fragment implements Information_Change {

    private static final String GAME_MODE_ARG = "gamification_State";
    private static final String DAILY_REVIEW_DONE_ARG = "Daily_Done";
    private static final String ACCOUNT_NAME_ARG = "Account_Name";
    private static final String MODE_1 = "Mode 1";
    private static final String MODE_2 = "Mode 2";
    private static final String MODE_3 = "Mode 3";

    private String gamification_Mode;
    private String account_name;
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

    /**
     * Enum to use with the Yellow_Fragment_Data_Listener interface
     */
    public enum Fields {
        export_Time, daily_Review, account_Information, state
    }

    public static Yellow_State_Fragment newInstance(String gamification_Mode, Boolean daily_Review, String account_Name) {
        Yellow_State_Fragment yellow_state_fragment = new Yellow_State_Fragment();
        Bundle args = new Bundle();
        args.putString(GAME_MODE_ARG, gamification_Mode);
        args.putString(ACCOUNT_NAME_ARG, account_Name);
        args.putBoolean(DAILY_REVIEW_DONE_ARG, daily_Review);
        yellow_state_fragment.setArguments(args);
        return yellow_state_fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamification_Mode = getArguments().getString(GAME_MODE_ARG);
        //account_name = getArguments().getString(ACCOUNT_NAME_ARG);
        daily_Review_Required = getArguments().getBoolean(DAILY_REVIEW_DONE_ARG);
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

    /**
     * This private methods functionality is to control which mode is created in the view through a switch control for
     * the value of gamification_Mode. This has been separated out to better allow cohesiveness of the class and allow
     * for simpler modification at a later date.
     */
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


    /**
     * Setup gamification related buttons for Mode 1 gamification they are allowed to access. Enable Challenges. Is
     * missing some functionality as
     * these will call a
     * specific challenges section and avatar/store
     */
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

    /**
     * Setup gamification related buttons for Mode 2 gamification. Disable Gamification, Enable Challenges. Is missing
     * some functionality as these will call a specific challenges section and avatar/store
     */
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

    /**
     * Setup gamification related buttons for Mode 2 gamification. Disable Gamification and changes challenges to
     * activities.
     */
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

    /**
     * Setup all other buttons that don't change between gamification modes for the fragment.
     */
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
                //listener.export();
            }
        });
    }

    /**
     * Concrete implementation of update method from Information_Change interface. Used to update class fields to new
     * values while running and change display sections as required
     *
     * @param field Enum value for what to change
     * @param value Value to change to
     */
    @Override
    public void update(Field field, String value) {
        switch (field) {
            case Name:
                account_name = value;
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

    /**
     * Interface to allow the fragment to communicate back to the main activity.
     */
    public interface Yellow_Fragment_Data_Listener {
        void onChangedData(Fields field);

        void export();
    }

}
