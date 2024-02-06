package com.payamgr.androidbenchmark.ui.page.calculator

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.payamgr.androidbenchmark.data.model.NumType
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.data.state.CalculatorState

abstract class CalculatorVM(initialState: CalculatorState) :
    MavericksViewModel<CalculatorState>(initialState) {
    companion object :
        MavericksViewModelFactory<CalculatorVM, CalculatorState> by hiltMavericksViewModelFactory()

    abstract fun onNumChanged(numType: NumType, value: Int)
    abstract fun onOperationChanged(operation: Operation)
}
