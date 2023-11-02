package com.payamgr.androidbenchmark.data.model

import androidx.annotation.StringRes
import com.payamgr.androidbenchmark.R

enum class Operation(val label: String, @StringRes val descriptionId: Int) {
    Add("+", R.string.operation_addition),
    Sub("−", R.string.operation_subtraction),
    Mul("×", R.string.operation_multiplication),
    Div("÷", R.string.operation_division),
    Pow("∧", R.string.operation_power),
    Root("√", R.string.operation_root),
}
