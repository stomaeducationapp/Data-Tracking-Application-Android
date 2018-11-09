package capstonegroup2.dataapp.Account_Modification;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import java.util.Objects;

import capstonegroup2.dataapp.R;

//Code has been adapted from jeremy Dunnet AccountCreation activity to keep the same format and information as creation
public class Account_Information_Notifications_Fragment extends DialogFragment {
    private static final String NOT_ARG = "notification";
    private String current_not_lvl;
    private OnNotificationFragmentInteractionListener mListener;
private CheckBox not;
    public Account_Information_Notifications_Fragment() {
        // Required empty public constructor
    }

    public static Account_Information_Notifications_Fragment newInstance(String param1) {
        Account_Information_Notifications_Fragment fragment = new Account_Information_Notifications_Fragment();
        Bundle args = new Bundle();
        args.putString(NOT_ARG, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.account__information__notifications__fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        current_not_lvl = getArguments().getString(NOT_ARG);
        not = view.findViewById(R.id.checkBox_Notifications);
        if(current_not_lvl.equals("Yes")){
            not.setChecked(true);
        }else{
            not.setChecked(false);
        }
        Button returnbtn = view.findViewById(R.id.not_rtn_btn);
        returnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value;
                if(not.isChecked())
                {
                    value = "Yes";
                }
                else
                {
                    value = "No";
                }
                mListener.onFinishNotificationDialog(value);
                dismiss();
            }
        });
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
        if (context instanceof OnNotificationFragmentInteractionListener) {
            mListener = (OnNotificationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnNotificationFragmentInteractionListener {
        void onFinishNotificationDialog(String notifications);
    }
}
