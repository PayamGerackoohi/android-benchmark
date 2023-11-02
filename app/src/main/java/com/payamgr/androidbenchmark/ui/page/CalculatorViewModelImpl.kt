package com.payamgr.androidbenchmark.ui.page

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.payamgr.androidbenchmark.data.model.NumType
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.data.state.CalculatorState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class CalculatorViewModelImpl @AssistedInject constructor(
    @Assisted initialState: CalculatorState,
) : CalculatorViewModel(initialState) {
    @AssistedFactory
    interface Factory : AssistedViewModelFactory<CalculatorViewModelImpl, CalculatorState>

    override fun onNumChanged(numType: NumType, value: Int) = setState {
        when (numType) {
            NumType.Num1 -> copy(num1 = value)
            NumType.Num2 -> copy(num2 = value)
        }
    }

    override fun onOperationChanged(operation: Operation) = setState { copy(operation = operation) }
}
