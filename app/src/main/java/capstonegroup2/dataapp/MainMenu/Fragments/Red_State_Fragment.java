package capstonegroup2.dataapp.MainMenu.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import capstonegroup2.dataapp.R;

public class Red_State_Fragment extends Fragment {
    private Button Reset_Btn;

    public enum Fields {state}

    private static final String RESET_STATE = "Green";
    Red_Fragment_Data_Listener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        listener = (Red_Fragment_Data_Listener) getActivity();

        View view = inflater.inflate(R.layout.green__state__fragment, container, false);
        Reset_Btn = view.findViewById(R.id.Review_Btn);

        Reset_Btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                listener.onChangedData(Fields.state, RESET_STATE);
            }
        });
        return view;
    }

    public interface Red_Fragment_Data_Listener {
        void onChangedData(Fields field, String value);

    }
}
