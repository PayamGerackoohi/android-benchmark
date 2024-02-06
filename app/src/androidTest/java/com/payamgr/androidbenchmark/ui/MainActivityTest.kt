package com.payamgr.androidbenchmark.ui

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.filters.LargeTest
import androidx.test.filters.SdkSuppress
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.util.Screenshot
import com.payamgr.androidbenchmark.util.assertHasRole
import com.payamgr.androidbenchmark.util.take
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityTest {
    @get:Rule
    val rule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    @SdkSuppress(minSdkVersion = 31)
    fun firstPageTest_31plus() {
        // Verify the initial state
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()
        rule.onNodeWithContentDescription("Slider Num 1, Value: 0").assertIsDisplayed()
        rule.onNodeWithContentDescription("Slider Num 2, Value: 0").assertIsDisplayed()
        rule.onNodeWithContentDescription("Result")
            .assertIsDisplayed()
            .assertTextEquals("0")

        // Slide the num1 slider to -3
        rule.onNodeWithContentDescription("Slider Num 1").performTouchInput { swipeLeft() }
        rule.onNodeWithContentDescription("Slider Num 1, Value: -10").assertExists()
        rule.onNodeWithContentDescription("Result").assertTextEquals("-10")

        // Slide the num2 slider to 3
        rule.onNodeWithContentDescription("Slider Num 2").performTouchInput { swipeRight() }
        rule.onNodeWithContentDescription("Slider Num 2, Value: 10").assertExists()
        rule.onNodeWithContentDescription("Result").assertTextEquals("0")

        // Verify the '+' Button
        rule.onNodeWithText(Operation.Add.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Addition")
            .assertHasRole(Role.Switch)
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result").assertTextEquals("0")

        // Verify the '-' Button
        rule.onNodeWithText(Operation.Sub.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Subtraction")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result").assertTextEquals("-20")

        // Verify the '*' Button
        rule.onNodeWithText(Operation.Mul.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Multiplication")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("-100")

        // Verify the '/' Button
        rule.onNodeWithText(Operation.Div.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Division")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("-1")

        // Verify the '^' Button
        rule.onNodeWithText(Operation.Pow.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Power")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("10.000.000.000")
        // .assertTextEquals("10,000,000,000") // todo english content

        // Verify the 'root' Button
        rule.onNodeWithText(Operation.Root.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Root")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("1,1973 + 0,389i")
//            .assertTextEquals("1.1973 + 0.389i") // todo english content

        Screenshot.Calculator.take()
    }

    @Test
    @SdkSuppress(maxSdkVersion = 30)
    fun firstPageTest_pre31() {
        // Verify the initial state
        rule.onNodeWithContentDescription("Splashscreen").isDisplayed()
        rule.onNodeWithTag("SplashScreen.IconBackground").isDisplayed()
        rule.onNodeWithContentDescription("Animated Vector: Splashscreen Icon").isDisplayed()
        rule.onNode(hasStateDescription("End")).assertExists()

        // Wait until the end of the animation
        rule.waitUntil {
            rule.onAllNodesWithTag("CalculatorView").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify the 'CalculatorView' page is displayed
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()

        // Verify the initial state
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()
        rule.onNodeWithContentDescription("Slider Num 1, Value: 0").assertIsDisplayed()
        rule.onNodeWithContentDescription("Slider Num 2, Value: 0").assertIsDisplayed()
        rule.onNodeWithContentDescription("Result")
            .assertIsDisplayed()
            .assertTextEquals("0")

        // Slide the num1 slider to -3
        rule.onNodeWithContentDescription("Slider Num 1").performTouchInput { swipeLeft() }
        rule.onNodeWithContentDescription("Slider Num 1, Value: -10").assertExists()
        rule.onNodeWithContentDescription("Result").assertTextEquals("-10")

        // Slide the num2 slider to 3
        rule.onNodeWithContentDescription("Slider Num 2").performTouchInput { swipeRight() }
        rule.onNodeWithContentDescription("Slider Num 2, Value: 10").assertExists()
        rule.onNodeWithContentDescription("Result").assertTextEquals("0")

        // Verify the '+' Button
        rule.onNodeWithText(Operation.Add.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Addition")
            .assertHasRole(Role.Switch)
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result").assertTextEquals("0")

        // Verify the '-' Button
        rule.onNodeWithText(Operation.Sub.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Subtraction")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result").assertTextEquals("-20")

        // Verify the '*' Button
        rule.onNodeWithText(Operation.Mul.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Multiplication")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("-100")

        // Verify the '/' Button
        rule.onNodeWithText(Operation.Div.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Division")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("-1")

        // Verify the '^' Button
        rule.onNodeWithText(Operation.Pow.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Power")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("10,000,000,000")

        // Verify the 'root' Button
        rule.onNodeWithText(Operation.Root.label)
            .assertIsDisplayed()
            .assertContentDescriptionEquals("Root")
            .assertHasRole(Role.Switch)
            .assertIsNotSelected()
            .performClick()
            .assertIsSelected()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("1.1973 + 0.389i")
    }
}
