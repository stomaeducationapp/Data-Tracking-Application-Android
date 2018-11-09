package capstonegroup2.dataapp.Account_Modification;

import android.app.DialogFragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import XML.XML_Writer;
import capstonegroup2.dataapp.R;

public class Account_Information_Password_Fragment extends DialogFragment {
    private XML_Writer xml_account_writer;

    private OnPasswordFragmentInteractionListener mListener;

    public Account_Information_Password_Fragment() {
        // Required empty public constructor
    }

    public static Account_Information_Name_Fragment newInstance() {
        Account_Information_Name_Fragment fragment = new Account_Information_Name_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.account__information__password__fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        /*Factory call when integrated
        xml_account_writer =
        xml_login_writer = */


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPasswordFragmentInteractionListener) {
            mListener = (OnPasswordFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPasswordFragmentInteractionListener {
        void onFinishPasswordDialog(String account_Name);
    }
}
