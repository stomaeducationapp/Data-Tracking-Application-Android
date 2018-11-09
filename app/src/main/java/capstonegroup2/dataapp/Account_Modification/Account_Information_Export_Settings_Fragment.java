package capstonegroup2.dataapp.Account_Modification;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;

import java.util.Objects;

import capstonegroup2.dataapp.R;


public class Account_Information_Export_Settings_Fragment extends DialogFragment {
    private OnExportFragmentInteractionListener mListener;
private String exportValue;
    public Account_Information_Export_Settings_Fragment() {
        // Required empty public constructor
    }

    public static Account_Information_Export_Settings_Fragment newInstance() {
        Account_Information_Export_Settings_Fragment fragment = new Account_Information_Export_Settings_Fragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.account__information__export__settings__fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Button rtn = view.findViewById(R.id.expt_Rtn_Btn);
        rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exportValue != "") {
                    mListener.onFinishExportDialog(exportValue);
                }
                dismiss();
            }
        });

    }

//Taken from Accountcreation by Jeremy Dunnet to keep the same format and values
    public void exportSet (View view)
    {
        boolean checked = ((RadioButton) view).isChecked(); //Find out if the button was checked

        // Check which radio button was clicked
        switch (view.getId())
        {
            case R.id.e1:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText()); //Set the value to the text of the button
                }
                break;
            case R.id.e2:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText());
                }
                break;
            case R.id.e3:
                if (checked)
                {
                    exportValue = (String) (((RadioButton) view).getText());
                }
                break;
            default:
                exportValue="";
                break;

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExportFragmentInteractionListener) {
            mListener = (OnExportFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnExportFragmentInteractionListener {
        void onFinishExportDialog(String export);
    }

}
