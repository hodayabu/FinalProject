package com.example.finalproject.UI.Activities;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.finalproject.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class FindMatchTest {

    @Rule
    public ActivityTestRule<FindMatch> mActivityTestRule = new ActivityTestRule<>(FindMatch.class);
    private FindMatch mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }


    @Test
    public void onCreate() {
        View view = mActivity.findViewById(R.id.askedLoansList);
        assertNotNull(view);
    }


    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}