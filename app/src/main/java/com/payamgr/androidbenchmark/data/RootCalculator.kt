package com.payamgr.androidbenchmark.data

import kotlin.math.pow

object RootCalculator {
    fun root(a: Int, b: Int): String = if (a < 0) {
        complexOf(
            if (b == 0) Float.NaN
            else (-a).toFloat().pow(1f / b.toFloat()),
            (Math.PI / b).toFloat()
        )
    } else
        a.toFloat().pow(1f / b.toFloat()).decimalFormat()

    fun complexOf(magnitude: Float, phase: Float) = if (magnitude.isFinite())
        Complex.polar(magnitude, phase).toString()
    else
        magnitude.decimalFormat()
}
