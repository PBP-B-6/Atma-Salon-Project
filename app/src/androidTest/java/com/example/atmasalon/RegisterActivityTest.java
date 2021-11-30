package com.example.atmasalon;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.atmasalon.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public ActivityTestRule<RegisterActivity> mActivityTestRule = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void registerActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutNama),
                                0),
                        1));
        textInputEditText.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction textInputEditText2 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutNama),
                                0),
                        0));
        textInputEditText2.perform(scrollTo(), replaceText("yotam"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction textInputEditText3 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutEmail),
                                0),
                        1));
        textInputEditText3.perform(scrollTo(), replaceText("yotam@gmail"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton3.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText4 = onView(
                allOf(withText("yotam@gmail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1)));
        textInputEditText4.perform(scrollTo(), click());

        ViewInteraction textInputEditText5 = onView(
                allOf(withText("yotam@gmail"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1)));
        textInputEditText5.perform(scrollTo(), replaceText("yotam@gmail.coom"));

        ViewInteraction textInputEditText6 = onView(
                allOf(withText("yotam@gmail.coom"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText6.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText7 = onView(
                allOf(withText("yotam@gmail.coom"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1)));
        textInputEditText7.perform(scrollTo(), click());

        ViewInteraction textInputEditText8 = onView(
                allOf(withText("yotam@gmail.coom"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1)));
        textInputEditText8.perform(scrollTo(), replaceText("yotam@gmail.com"));

        ViewInteraction textInputEditText9 = onView(
                allOf(withText("yotam@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText9.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction textInputEditText10 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutPasswordRegister),
                                0),
                        1));
        textInputEditText10.perform(scrollTo(), replaceText("asd"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton5.perform(scrollTo(), click());

        ViewInteraction textInputEditText11 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutRepeatPassword),
                                0),
                        1));
        textInputEditText11.perform(scrollTo(), replaceText("asdw"), closeSoftKeyboard());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton6.perform(scrollTo(), click());

        ViewInteraction textInputEditText12 = onView(
                allOf(withText("asdw"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutRepeatPassword),
                                        0),
                                1)));
        textInputEditText12.perform(scrollTo(), click());

        ViewInteraction textInputEditText13 = onView(
                allOf(withText("asdw"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutRepeatPassword),
                                        0),
                                1)));
        textInputEditText13.perform(scrollTo(), replaceText("asd"));

        ViewInteraction textInputEditText14 = onView(
                allOf(withText("asd"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutRepeatPassword),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText14.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton7.perform(scrollTo(), click());

        ViewInteraction materialRadioButton = onView(
                allOf(withId(R.id.radioPria), withText("Pria"),
                        childAtPosition(
                                allOf(withId(R.id.radioGroupKelamin),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                6)),
                                0)));
        materialRadioButton.perform(scrollTo(), click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton8.perform(scrollTo(), click());

        ViewInteraction textInputEditText15 = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.inputLayoutPhone),
                                0),
                        1));
        textInputEditText15.perform(scrollTo(), replaceText("081"), closeSoftKeyboard());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton9.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText16 = onView(
                allOf(withText("081"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText16.perform(scrollTo(), click());

        ViewInteraction textInputEditText17 = onView(
                allOf(withText("081"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText17.perform(scrollTo(), replaceText("021137293722"));

        ViewInteraction textInputEditText18 = onView(
                allOf(withText("021137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText18.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton10.perform(scrollTo(), click());

        ViewInteraction textInputEditText19 = onView(
                allOf(withText("021137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText19.perform(scrollTo(), click());

        ViewInteraction textInputEditText20 = onView(
                allOf(withText("021137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText20.perform(scrollTo(), replaceText("081137293722"));

        ViewInteraction textInputEditText21 = onView(
                allOf(withText("081137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText21.perform(closeSoftKeyboard());

        ViewInteraction textInputEditText22 = onView(
                allOf(withText("081137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText22.perform(scrollTo(), click());

        ViewInteraction textInputEditText23 = onView(
                allOf(withText("081137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText23.perform(scrollTo(), replaceText("08113729372a"));

        ViewInteraction textInputEditText24 = onView(
                allOf(withText("08113729372a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText24.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton11.perform(scrollTo(), click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textInputEditText25 = onView(
                allOf(withText("08113729372a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText25.perform(scrollTo(), click());

        ViewInteraction textInputEditText26 = onView(
                allOf(withText("08113729372a"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1)));
        textInputEditText26.perform(scrollTo(), replaceText("081137293722"));

        ViewInteraction textInputEditText27 = onView(
                allOf(withText("081137293722"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutPhone),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText27.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton12.perform(scrollTo(), click());

        ViewInteraction textInputEditText28 = onView(
                allOf(withText("yotam@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1)));
        textInputEditText28.perform(scrollTo(), replaceText("yotamniki@gmail.com"));

        ViewInteraction textInputEditText29 = onView(
                allOf(withText("yotamniki@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.inputLayoutEmail),
                                        0),
                                1),
                        isDisplayed()));
        textInputEditText29.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.btnDaftar), withText("Daftar"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                8)));
        appCompatButton13.perform(scrollTo(), click());
    }

    private static Matcher<View> childAtPosition(
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
