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

public class Yellow_State_Fragment extends Fragment {
    public enum Fields {state}

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
    private Button Challenges_Btn;
    private Button Export_Btn;
    Yellow_Fragment_Data_Listener listener;
    News_PlaceHolder news;

    Time_Observer export_Data_Obs;
    Time_Observer daily_Review_Obs;
    Form_Change_Observer form_Switcher_Obs;

    public static Yellow_State_Fragment newInstance(String gamification_Mode/*, List<> gamification_Data (placeholder for information when gamification functionality is created*/, Boolean daily_Review) {
        Yellow_State_Fragment yellow_state_fragment = new Yellow_State_Fragment();
        Bundle args = new Bundle();
        args.putString(GAME_MODE_ARG, gamification_Mode);
        args.putBoolean(EXPORT_DONE_ARG, daily_Review);
        yellow_state_fragment.setArguments(args);
        return yellow_state_fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gamification_Mode = getArguments().getString(GAME_MODE_ARG);
        daily_Review = getArguments().getBoolean(EXPORT_DONE_ARG);
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
        Factory factory = Factory.Get_Factory();
        export_Data_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Export_Data);
        daily_Review_Obs = factory.Make_Time_Observer(Factory.Time_Observer_Choice.Daily_Review);
        form_Switcher_Obs = factory.Make_Form_Change_Observer();
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
    //Setup both buttons for full gamification
    private void Mode_1_Creation() {
        Generic_Button_Creation();
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

    public interface Yellow_Fragment_Data_Listener {
        void onChangedData(Fields field, String value);

    }

}
