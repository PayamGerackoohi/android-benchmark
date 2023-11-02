package com.payamgr.androidbenchmark.ui

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.filters.LargeTest
import com.payamgr.androidbenchmark.data.model.Operation
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {
    @get:Rule
    val rule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun firstPageTest() {
//        rule.activityRule.scenario.onActivity {}
        rule.onNodeWithText(Operation.Add.label).assertExists()
        rule.onNodeWithText(Operation.Sub.label).assertExists()
    }
}
