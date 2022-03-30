package com.bintangfajarianto.submission3.ui.setting

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.bintangfajarianto.submission3.R
import org.junit.Before
import org.junit.Test

class SettingActivityTest {
    @Before
    fun setup(){
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
    fun assertChangeSwitch() {
        onView(withId(R.id.switch_theme))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.switch_theme)).perform(ViewActions.click())
        onView(withId(R.id.switch_theme))
            .check(ViewAssertions.matches(ViewMatchers.isChecked()))
    }
}