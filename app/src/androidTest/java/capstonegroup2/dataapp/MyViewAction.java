package capstonegroup2.dataapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import org.hamcrest.Matcher;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 17/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 17/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is a test helper class that allows easy clicking of an item within a RecyclerView
 */

/* VERSION HISTORY
 * 17/10/2018 - Created file
 */

/* REFERENCES
 * All code was adapted from https://stackoverflow.com/questions/28476507/using-espresso-to-click-view-inside-recyclerview-item
 * And many more from https://developer.android.com/
 */

public class MyViewAction {

    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }

    public static ViewAction checkColourChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
            }
        };
    }

}