package com.payamgr.androidbenchmark.ui.page.calculator

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import androidx.test.filters.LargeTest
import com.airbnb.mvrx.mocking.MockableMavericks
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.data.state.CalculatorState
import com.payamgr.androidbenchmark.util.app
import com.payamgr.androidbenchmark.util.assertHasRole
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

@LargeTest
class CalculatorTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun choice_selected_Test() {
        val callback = mockCallback()
        rule.setContent {
            CalculatorView.Choice(
                operation = Operation.Add,
                isSelected = true,
                callback
            )
        }
        rule.onNodeWithText(Operation.Add.label)
            .assertIsDisplayed()
            .assertIsSelected()
            .assertIsFocused()
            .assertHasRole(Role.Switch)
            .assertContentDescriptionEquals("Addition")
            .performClick()
        verify { callback() }
    }

    @Test
    fun choice_notSelected_Test() {
        val callback = mockCallback()
        every { callback() } just runs
        rule.setContent {
            CalculatorView.Choice(
                operation = Operation.Root,
                isSelected = false,
                callback
            )
        }
        rule.onNodeWithText(Operation.Root.label)
            .assertIsDisplayed()
            .assertIsNotSelected()
            .assertIsNotFocused()
            .assertHasRole(Role.Switch)
            .assertContentDescriptionEquals("Root")
            .performClick()
        verify { callback() }
    }

    @Test
    fun multiChoiceTest() {
        val currentOperation = mutableStateOf(Operation.Div)
        rule.setContent {
            CalculatorView.MultiChoice(
                choices = Operation.values(),
                selected = currentOperation.value,
                onSelected = { currentOperation.value = it })
        }
        // check initial states
        Operation.values().forEach { op ->
            if (op == Operation.Div)
                rule.onNodeWithText(op.label)
                    .assertIsDisplayed()
                    .assertIsSelected()
            else
                rule.onNodeWithText(op.label)
                    .assertIsDisplayed()
                    .assertIsNotSelected()
        }

        // click on '-'
        rule.onNodeWithText(Operation.Sub.label).performClick()
        Operation.values().forEach { op ->
            if (op == Operation.Sub)
                rule.onNodeWithText(op.label)
                    .assertIsDisplayed()
                    .assertIsSelected()
            else
                rule.onNodeWithText(op.label)
                    .assertIsDisplayed()
                    .assertIsNotSelected()
        }
    }

    @Test
    fun numRowTest() {
        val value = mutableStateOf(0)
        rule.setContent {
            CalculatorView.NumRow(
                "1",
                range = -3f..3f,
                value = value.value,
                onValueChange = { value.value = it },
            )
        }

        // Check Initial State
        rule.onNodeWithText("0")
            .assertContentDescriptionEquals("Slider 1, Value: 0")
            .assertExists()


        // slide to the end (3)
        rule.onNodeWithContentDescription("Slider 1")
            .performTouchInput { swipeRight() }
        rule.onNodeWithText("3")
            .assertContentDescriptionEquals("Slider 1, Value: 3")
            .assertExists()

        // Slide to the Start (-3)
        rule.onNodeWithContentDescription("Slider 1")
            .performTouchInput { swipeLeft() }
        rule.onNodeWithText("-3")
            .assertContentDescriptionEquals("Slider 1, Value: -3")
            .assertExists()
    }

    @Test
    fun pageTest() {
        MockableMavericks.initialize(app)
        rule.setContent {
            CalculatorView.Page(CalculatorVMImpl(CalculatorState()))
        }

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
    }

    private fun mockCallback() = mockk<() -> Unit>().also { callback ->
        every { callback() } just runs
    }
}
