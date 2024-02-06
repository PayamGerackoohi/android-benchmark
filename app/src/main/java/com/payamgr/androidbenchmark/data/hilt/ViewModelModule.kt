package com.payamgr.androidbenchmark.data.hilt

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.payamgr.androidbenchmark.ui.page.calculator.CalculatorVM
import com.payamgr.androidbenchmark.ui.page.calculator.CalculatorVMImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CalculatorVM::class)
    fun calculatorVM(factory: CalculatorVMImpl.Factory): AssistedViewModelFactory<*, *>
}
