package com.example.casper;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.casper.data.BookSaver;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListViewMainActivityTest {
    @Rule
    public ActivityTestRule<ListViewMainActivity> mActivityTestRule = new ActivityTestRule<>(ListViewMainActivity.class);
    private BookSaver bookKeeper;
    private Context context;

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

    @Before
    public void setUp() throws Exception {
        context = ApplicationProvider.getApplicationContext();
        bookKeeper = new BookSaver(context);
        bookKeeper.load();
    }

    @After
    public void tearDown() throws Exception {
        bookKeeper.save();
    }

    @Test
    public void listViewMainActivityTest() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.text_view_book_title), withText("创新工程实践,0.0元"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        1),
                                1),
                        isDisplayed()));
        textView.perform(longClick());

        ViewInteraction menuUpdate = onView(
                allOf(withId(android.R.id.title), withText("修改"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("com.android.internal.view.menu.ListMenuItemView")),
                                        0),
                                0),
                        isDisplayed()));
        menuUpdate.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.edit_text_book_title), withText("创新工程实践"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("创新工程实践1"));

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.edit_text_book_price), withText("0.0"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("0.01"));

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.button_ok), withText("确定"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.text_view_book_title), withText("创新工程实践1,0.01元"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.list_view_books),
                                        1),
                                1),
                        isDisplayed()));
        mActivityTestRule.finishActivity();
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
