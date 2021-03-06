package capstonegroup2.dataapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.HashMap;

import Factory.Factory;
import MedicalReview.DailyReview;
import Observers.Daily_Review;
import Observers.Form_Change;
import Observers.Form_Change_Observer;
import Observers.Invalid_Enum_Exception;
import Observers.Time_Observer;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 17/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 21/01/2019
 */

/* CLASS/FILE DESCRIPTION
 * This is the GUI that handles integration tests of activities that need intents but are not connected to a finished activity yet
 */

/* VERSION HISTORY
 * 17/12/2018 - Created file and added first test code
 * 21/01/2019 - Rewrote to match Daily review test harness
 * 12/02/2019 - Rewrote to match MedicalInput test harness
 */

/* REFERENCES
 * And many more from https://developer.android.com/
 */

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Factory f= Factory.Get_Factory();
        final Form_Change fc = (Form_Change) f.Make_Form_Change_Observer();

        File medFile = new File(this.getFilesDir().getPath() + "/accounts/medical_file.xml");
        final HashMap<Time_Observer.Files, File> files = new HashMap<Time_Observer.Files, File>();
        files.put(Time_Observer.Files.Medical, medFile);

        final Bundle bundle = new Bundle();
        bundle.putSerializable("fileMap", files);

        final Context context = this;

        Button testButt = findViewById(R.id.testButt);
        testButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fc.Change_Form_Bundle(Form_Change_Observer.Activity_Control.Medical_Data_Input, context, bundle);
                } catch (Invalid_Enum_Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
