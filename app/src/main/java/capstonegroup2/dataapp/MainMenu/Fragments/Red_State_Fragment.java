package capstonegroup2.dataapp.MainMenu.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import capstonegroup2.dataapp.R;

/**
 * The Reg_State_Fragment is used to contain all the information and functionality to show tot he user on the main
 * activity. This fragment is used when the users health is detected to be dangerous. There is no functionality and all
 * that is displayed is information to keep themselves from deteriorating and to contact medical professional. The reset
 * button is there to reset the app back to green when they have been treated or the app has made a mistake.
 */
public class Red_State_Fragment extends Fragment {
    private Button Reset_Btn;

    /**
     * Enum to use with the Red_Fragment_Data_Listener interface
     */
    public enum Fields {
        state
    }

    Red_Fragment_Data_Listener listener;

    public static Fragment newInstance() {
        return new Red_State_Fragment();
    }


    /**
     * Override of the onCreateView() method. This method will create the callback listener to the main activity,
     * retrieve references to all object on the GUI to be setup.
     * All the buttons functionality is added in this method
     *
     * @return view of this class
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listener = (Red_Fragment_Data_Listener) getActivity();

        View view = inflater.inflate(R.layout.red__state__fragment, container, false);
        Reset_Btn = view.findViewById(R.id.reset_Btn);

        Reset_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                listener.onChangedData(Fields.state);
            }
        });
        return view;
    }

    /**
     * Interface to allow the fragment to communicate back to the main activity.
     */
    public interface Red_Fragment_Data_Listener {
        void onChangedData(Fields field);

    }
}
