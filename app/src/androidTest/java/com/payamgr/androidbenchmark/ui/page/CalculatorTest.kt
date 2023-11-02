package com.payamgr.androidbenchmark.ui.page

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
        rule.setContent { CalculatorView.Choice(operation = Operation.Add, isSelected = true, callback) }
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
    fun choice_noSelected_Test() {
        val callback = mockCallback()
        every { callback() } just runs
        rule.setContent { CalculatorView.Choice(operation = Operation.Root, isSelected = false, callback) }
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
        // check initial state
        rule.onNodeWithText("0")
            .assertContentDescriptionEquals("Slider 1, Value: 0")
            .assertExists()

        // slide -> 3
        rule.onNodeWithContentDescription("Slider 1")
            .performTouchInput { swipeRight() }
        rule.onNodeWithText("3")
            .assertContentDescriptionEquals("Slider 1, Value: 3")
            .assertExists()

        // slide -> -3
        rule.onNodeWithContentDescription("Slider 1")
            .performTouchInput { swipeLeft() }
        rule.onNodeWithText("-3")
            .assertContentDescriptionEquals("Slider 1, Value: -3")
            .assertExists()
    }

    @Test
    fun pageTest() {
        MockableMavericks.initialize(app)
        rule.setContent { CalculatorView.Page(CalculatorViewModelImpl(CalculatorState())) }

        // check initial state
        rule.onNodeWithContentDescription("Slider Num 1, Value: 0").assertExists()
        rule.onNodeWithContentDescription("Slider Num 2, Value: 0").assertExists()
        rule.onNodeWithContentDescription("Result")
            .assertExists()
            .assertTextEquals("0")

        // num1.slide -> -3
        rule.onNodeWithContentDescription("Slider Num 1").performTouchInput { swipeLeft() }
        rule.onNodeWithContentDescription("Slider Num 1, Value: -10").assertExists()
        rule.onNodeWithContentDescription("Result").assertTextEquals("-10")

        // num2.slide -> 3
        rule.onNodeWithContentDescription("Slider Num 2").performTouchInput { swipeRight() }
        rule.onNodeWithContentDescription("Slider Num 2, Value: 10").assertExists()
        rule.onNodeWithContentDescription("Result").assertTextEquals("0")

        // click '-'
        rule.onNodeWithText(Operation.Sub.label).performClick()
        rule.onNodeWithContentDescription("Result").assertTextEquals("-20")

        // click '*'
        rule.onNodeWithText(Operation.Mul.label).performClick()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("-100")

        // click '/'
        rule.onNodeWithText(Operation.Div.label).performClick()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("-1")

        // click '^'
        rule.onNodeWithText(Operation.Pow.label).performClick()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("10,000,000,000")

        // click 'root'
        rule.onNodeWithText(Operation.Root.label).performClick()
        rule.onNodeWithContentDescription("Result")
            .assertTextEquals("1.1973 + 0.389i")
    }

    private fun mockCallback() = mockk<() -> Unit>().also { callback ->
        every { callback() } just runs
    }
}
