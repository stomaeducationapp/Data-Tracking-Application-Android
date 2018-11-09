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
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import XML.XML_Writer;
import capstonegroup2.dataapp.R;

/**
 * Code taken from Account Creation by Jeremy Dunnet to conform with layout and feedback between modification and
 * creation
 */
public class Account_Information_Name_Fragment extends DialogFragment {

    /**
     * Name of the Account file for each account
     */
    private static final String ACCOUNT_FILE_NAME = "AccountInformationFile.xml";

    /**
     * Name of the Login file for the application
     */
    private static final String LOGIN_FILE_NAME = "LoginInformation.xml";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "account";
    // TODO: Rename and change types of parameters
    private String current_Account_Name;
    private EditText account_Name_TextView;
    private TextView current_Account_Name_View;
    private OnAccountFragmentInteractionListener mListener;
    private XML_Writer xml_account_writer;
    private XML_Writer xml_login_writer;
    //Boolean for text change
    private Boolean account_Key_Check;

    private EditText account_Name_Feedback;


    public Account_Information_Name_Fragment() {
        // Required empty public constructor
    }

    public static Account_Information_Name_Fragment newInstance(String param1) {
        Account_Information_Name_Fragment fragment = new Account_Information_Name_Fragment();

        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.account__information__name__fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        current_Account_Name = getArguments().getString(ARG_PARAM1);
        /*Factory call when integrated
        xml_account_writer =
        xml_login_writer = */
        current_Account_Name_View = view.findViewById(R.id.current_Account_Name_View);
        final Button modify_Account_Name_Btn = view.findViewById(R.id.account_Name_Submit_Btn);
        final Button return_To_Main_Act = view.findViewById(R.id.acct_Info_Return_Btn);
        account_Name_TextView = view.findViewById(R.id.account_Name_Text);
        account_Key_Check = false;
        current_Account_Name_View.setText(current_Account_Name);
        modify_Account_Name_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String new_Account_Name = account_Name_TextView.getText().toString();
                //Need to validate account name, may return an enum so need to check against that as well
                //If(Validate_information.accountname(new_Account_Name){
                current_Account_Name_View.setText(new_Account_Name);
                //}else{
                // failed and need to let them know through toast
                //}

            }
        });
        return_To_Main_Act.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mListener.onFinishAccountDialog(current_Account_Name_View.getText().toString());
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
        if (context instanceof OnAccountFragmentInteractionListener) {
            mListener = (OnAccountFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnAccountFragmentInteractionListener {
        void onFinishAccountDialog(String account_Name);
    }
}