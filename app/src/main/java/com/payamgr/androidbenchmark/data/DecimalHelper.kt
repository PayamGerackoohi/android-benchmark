package com.payamgr.androidbenchmark.data

import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.pow

object DecimalHelper {
    const val DefaultPrecision = 4

    val defaultDecimalFormat get() = DecimalFormat(decimalPatternOf(DefaultPrecision))

    fun decimalPatternOf(digits: Int) = if (digits < 0) ""
    else buildString {
        append(",###")
        if (digits != 0)
            append(".")
        repeat(digits) {
            append("#")
        }
    }

    fun epsilonOf(precision: Int) = 10.0.pow(-precision.toDouble()).toFloat()
}

fun Float.isCloseTo(other: Float, epsilon: Float) = abs(this - other) < abs(epsilon)

fun Int.decimalFormat(): String = DecimalFormat(",###").format(this)

fun Float.decimalFormat(): String = when {
    isNaN() -> this.toString()
    else -> DecimalHelper.defaultDecimalFormat.format(this)
}
