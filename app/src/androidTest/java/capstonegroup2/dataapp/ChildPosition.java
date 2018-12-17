package capstonegroup2.dataapp;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/* AUTHOR INFORMATION
 * CREATOR - Jeremy Dunnet 17/12/2018
 * LAST MODIFIED BY - Jeremy Dunnet 17/12/2018
 */

/* CLASS/FILE DESCRIPTION
 * This is a test helper class that allows easier finding of a view within a RecyclerView (for assertions)
 */

/* VERSION HISTORY
 * 17/10/2018 - Created file
 */

/* REFERENCES
 * All code was adapted from a espresso recording test
 * And many more from https://developer.android.com/
 */

public class ChildPosition{

    public static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
