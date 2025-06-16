package algonquin.cst2335.gurshan;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Espresso UI tests for the Password Checker app.
 * Checks for all password requirements and expected results.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /** Test password missing upper case letter. */
    @Test
    public void testMissingUpperCase() {
        onView(withId(R.id.editText)).perform(replaceText("password1#"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("You shall not pass!")));
    }

    /** Test password missing lower case letter. */
    @Test
    public void testMissingLowerCase() {
        onView(withId(R.id.editText)).perform(replaceText("PASSWORD1#"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("You shall not pass!")));
    }

    /** Test password missing a digit. */
    @Test
    public void testMissingDigit() {
        onView(withId(R.id.editText)).perform(replaceText("Password#"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("You shall not pass!")));
    }

    /** Test password missing a special character. */
    @Test
    public void testMissingSpecial() {
        onView(withId(R.id.editText)).perform(replaceText("Password1"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("You shall not pass!")));
    }

    /** Test password that meets all requirements. */
    @Test
    public void testAllRequirementsMet() {
        onView(withId(R.id.editText)).perform(replaceText("Password1#"));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Your password meets the requirements")));
    }
}
