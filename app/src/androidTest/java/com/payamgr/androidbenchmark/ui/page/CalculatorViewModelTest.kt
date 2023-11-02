package com.payamgr.androidbenchmark.ui.page

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.filters.MediumTest
import com.airbnb.mvrx.mocking.MockableMavericks
import com.payamgr.androidbenchmark.data.model.NumType
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.data.state.CalculatorState
import com.payamgr.androidbenchmark.util.app
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
@MediumTest
class CalculatorVMTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun event_Test() = runTest {
        MockableMavericks.initialize(app)
        val viewModel = CalculatorViewModelImpl(CalculatorState())

        // check initial State
        Assertions.assertThat(viewModel.awaitState()).isEqualTo(CalculatorState())

        // change num1
        viewModel.onNumChanged(NumType.Num1, 23)
        Assertions.assertThat(viewModel.awaitState()).isEqualTo(CalculatorState(num1 = 23))
//
        // change num2
        viewModel.onNumChanged(NumType.Num2, 32)
        Assertions.assertThat(viewModel.awaitState()).isEqualTo(CalculatorState(num1 = 23, num2 = 32))

        // changeOperation
        viewModel.onOperationChanged(Operation.Mul)
        val newState = CalculatorState(num1 = 23, num2 = 32, operation = Operation.Mul)
        Assertions.assertThat(viewModel.awaitState()).isEqualTo(newState)
    }
}
