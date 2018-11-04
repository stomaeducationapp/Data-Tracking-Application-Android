package capstonegroup2.dataapp.MainMenu.Fragments;

import android.app.Fragment;
import android.os.Bundle;

import capstonegroup2.dataapp.MainMenu.News_Package.News_PlaceHolder;

public class Green_State_Fragment extends Fragment {
    private static final String GAME_MODE_ARG = "gamification_State";
    private static final String MODE_1 = "Mode 1";
    private static final String MODE_2 = "Mode 2";
    private static final String MODE_3 = "Mode 3";
    private String gamification_Mode;
    Green_Fragment_Data_Listener listener;
    News_PlaceHolder news;
    public static Green_State_Fragment newInstance(String gamification_Mode/*, List<> gamification_Data (placeholder for information when gamification functionality is created*/) {
        Green_State_Fragment green_state_fragment = new Green_State_Fragment();
        Bundle args = new Bundle();
        args.putString(GAME_MODE_ARG, gamification_Mode);
        green_state_fragment.setArguments(args);
        return green_state_fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (Green_Fragment_Data_Listener) getActivity();
        gamification_Mode = getArguments().getString(GAME_MODE_ARG);
        //Setup gamification and set names or disable some buttons depending on this mode
        // Will also determine if the avatar is showing or that section will show other things
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
            //is an error will need to stop the creation of the fragment
        }
        //Add news information
        news = new News_PlaceHolder();
        // TODO: 04-Nov-18 get the  = findViewById(R.id.News); 
    }

private void Mode_1_Creation(){

}
private void Mode_2_Creation(){

}

private void Mode_3_Creation(){}

    public interface Green_Fragment_Data_Listener {
        void onChangedData(String field, String value);

    }
}
