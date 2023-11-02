package com.payamgr.androidbenchmark.data.hilt

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.payamgr.androidbenchmark.ui.page.CalculatorViewModel
import com.payamgr.androidbenchmark.ui.page.CalculatorViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CalculatorViewModel::class)
    fun provideCalculatorViewModel(factory: CalculatorViewModelImpl.Factory): AssistedViewModelFactory<*, *>
}
