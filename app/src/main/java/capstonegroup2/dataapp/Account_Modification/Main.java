package capstonegroup2.dataapp.Account_Modification;

import android.accounts.Account;
import android.app.Activity;
import android.os.Bundle;

import Account_Information.Account_Data;

public class Main extends Activity {
private Account_Data account_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
