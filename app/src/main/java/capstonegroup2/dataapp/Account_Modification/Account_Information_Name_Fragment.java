package capstonegroup2.dataapp.Account_Modification;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import XML.Account_Writer;
import XML.Login_Writer;
import XML.XML_Writer;
import capstonegroup2.dataapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Account_Information_Name_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Account_Information_Name_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account_Information_Name_Fragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "account";

    // TODO: Rename and change types of parameters
    private String current_Account_Name;
    private EditText account_Name_TextView;
    private View view;
    private OnFragmentInteractionListener mListener;
    private XML_Writer xml_account_writer;
    private XML_Writer xml_login_writer;

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
    // TODO: Rename and change types and number of parameters
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
        current_Account_Name = getArguments().getString(ARG_PARAM1);
        xml_account_writer = new Account_Writer();
        xml_login_writer = new Login_Writer();
        view = inflater.inflate(R.layout.fragment_account__information__name_, container, false);
        account_Name_TextView = view.findViewById(R.id.new_Account_Name_Input);
        final Button modify_Account_Name_Btn = view.findViewById(R.id.account_Name_Submit_Btn);
        modify_Account_Name_Btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: 12-Oct-18 will need a loop that trys x amount of times, otherwise file deleted/corrupt and need to remake them
                String new_Account_Name = account_Name_TextView.getText().toString();
                // TODO: 12-Oct-18 Add talking to Validate section -> is account name valid and doesn't exist
                //if(validate account name){
                Map<String, String> login_Map = new HashMap<>();
                login_Map.put(XML_Writer.Tags_To_Write.New_Account_Name.toString(), new_Account_Name);
                login_Map.put(XML_Writer.Tags_To_Write.Account_Name.toString(), current_Account_Name);
                Map<String, String> account_Map = new HashMap<>();
                account_Map.put(XML_Writer.Tags_To_Write.Name.toString(), current_Account_Name);
                // TODO: 12-Oct-18 when combining add in function calls to xml writer
                /*if (xml_login_writer.Write_File(, login_Map, XML_Writer.Tags_To_Write.New_Account_Name)) {
                    if (xml_account_writer.Write_File(, account_Map, XML_Writer.Tags_To_Write.Name)) {

                    }
                }*/
                //}else{
                //Need to inform user what is wrong, get info on that from Jezza
                //}
//call xml writer
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
