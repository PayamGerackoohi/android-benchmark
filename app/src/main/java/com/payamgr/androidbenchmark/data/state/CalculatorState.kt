package com.payamgr.androidbenchmark.data.state

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.PersistState
import com.payamgr.androidbenchmark.data.RootCalculator
import com.payamgr.androidbenchmark.data.decimalFormat
import com.payamgr.androidbenchmark.data.model.Operation
import kotlin.math.pow

data class CalculatorState(
    @PersistState val num1: Int = 0,
    @PersistState val num2: Int = 0,
    @PersistState val operation: Operation = Operation.Add,
) : MavericksState {
    val result: String by lazy {
        when (operation) {
            Operation.Add -> (num1 + num2).decimalFormat()
            Operation.Sub -> (num1 - num2).decimalFormat()
            Operation.Mul -> (num1 * num2).decimalFormat()
            Operation.Div -> (num1.toFloat() / num2.toFloat()).decimalFormat()
            Operation.Pow -> num1.toDouble().pow(num2.toDouble()).toFloat().decimalFormat()
            Operation.Root -> RootCalculator.root(num1, num2)
        }
    }
}

