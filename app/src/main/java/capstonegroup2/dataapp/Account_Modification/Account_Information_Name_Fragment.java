package capstonegroup2.dataapp.Account_Modification;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAccountFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Account_Information_Name_Fragment#newInstance} factory method to
 * create an instance of this fragment.
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
    private static final int ACCOUNT_NAME_MAX_LENGTH = 20;
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment Account_Information_Name_Fragment.
     */
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

        return inflater.inflate(R.layout.fragment__account__information__name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        current_Account_Name = getArguments().getString(ARG_PARAM1);
        /*Factory call when integrated
        xml_account_writer =
        xml_login_writer = */
        account_Name_TextView = view.findViewById(R.id.new_Account_Name_Input);
        current_Account_Name_View = view.findViewById(R.id.current_Account_Name_Text);

        final Button modify_Account_Name_Btn = view.findViewById(R.id.account_Name_Submit_Btn);
        final Button return_To_Main_Act = view.findViewById(R.id.acct_Info_Return_Btn);
        account_Key_Check = false;
        current_Account_Name_View.setText(current_Account_Name);
        TextWatcher unInputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String text = s.toString(); //Get the string\
                //If the text in the box is empty - don't bother with checking the characters inside
                if ((text.length() - 1) >= 0) {
                    //Check for violating length limit
                    if (text.length() > ACCOUNT_NAME_MAX_LENGTH) {
                        //Remove the added character
                        account_Name_Feedback.setText((text.substring(0, (text.length() - 1))));
                        //Display warning to the user
                        account_Name_Feedback.setError("Usernames have a maximum length of 20 characters");
                        account_Name_Feedback.setSelection((account_Name_Feedback.getText().length()));
                    } else {
                        //If there is an unaccepted character in the string
                        if (text.matches(".*[^a-zA-Z0-9_].*") && (!account_Key_Check)) {
                            //Remove all matches to this regex
                            text = text.replaceAll("[^a-zA-Z0-9_]", "");
                            account_Name_Feedback.setText(text); //Replace text to corrected string
                            //Display warning to the user
                            account_Name_Feedback.setError("Usernames can only have A-Z, 0-9 and _\nSpecial Characters are not permitted");
                            account_Name_Feedback.setSelection((account_Name_Feedback.getText().length()));
                            account_Key_Check = true; //Say we have changed a key (since by editing the string we may call this method recursively)
                        }
                    }

                }
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                account_Key_Check = false;


            }
        };
        modify_Account_Name_Btn.setOnClickListener(new View.OnClickListener()

        {
            public void onClick(View v) {
                // TODO: 12-Oct-18 will need a loop that trys x amount of times, otherwise file deleted/corrupt and need to remake them
                String new_Account_Name = account_Name_TextView.getText().toString();
                if (new_Account_Name.equals("")) {
                    account_Name_Feedback.requestFocus();
                    account_Name_Feedback.setError("Please provide a username");
                } else {
                    // TODO: 12-Oct-18 Add talking to Validate section -> is account name valid and doesn't exist
                    /*if(validate_Account_Name(new_Account_Name)){
                        Map<String, String> login_Map = new HashMap<>();
                        login_Map.put(XML_Writer.Tags_To_Write.New_Account_Name.toString(), new_Account_Name);
                        login_Map.put(XML_Writer.Tags_To_Write.Account_Name.toString(), current_Account_Name);
                        Map<String, String> account_Map = new HashMap<>();
                        account_Map.put(XML_Writer.Tags_To_Write.Name.toString(), current_Account_Name);
                        Context context = v.getContext();

                        File login_File = (context.getFilesDir() + "/Login_Information/"+ LOGIN_FILE_NAME);
                        if(xml_login_writer.Write_File(login_File, login_Map, XML_Writer.Tags_To_Write.New_Account_Name)){
                            File account_File = (context.getFilesDir() + "/" + current_Account_Name + "/" + ACCOUNT_FILE_NAME);
                            if (xml_account_writer.Write_File(account_File, account_Map, XML_Writer.Tags_To_Write.Name)){
                                OnFragmentInteractionListener mlistener = (OnFragmentInteractionListener) getActivity();
                                current_Account_Name = new_Account_Name;
                                current_Account_Name_View.setText(current_Account_Name);
                                mlistener.onFinishAccountDialog(current_Account_Name);
                                account_Name_Feedback.requestFocus();
                                account_Name_Feedback.setText("Account name changed successfully");
                            }else{
                            //Wrong information given or file doesn't exist
                            account_Name_Feedback.requestFocus();
                            account_Name_Feedback.setError("Something went wrong when changing account name");
                            }
                        }else{
                            //Wrong information given or file doesn't exist
                            account_Name_Feedback.requestFocus();
                            account_Name_Feedback.setError("Something went wrong when changing account name");
                        }
                     }*/
                    //Test and demo code uncomment or remove when enabling other code
                    current_Account_Name_View.setText(current_Account_Name);
                }
            }
        });
        return_To_Main_Act.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().onBackPressed();
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
