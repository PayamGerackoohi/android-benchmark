package com.payamgr.androidbenchmark.data

import kotlin.math.pow

object RootCalculator {
    fun root(a: Int, b: Int): String = if (a < 0) {
        val magnitude = if (b == 0) Double.NaN
        else (-a).toDouble().pow(1.0 / b.toDouble())
        complexOf(magnitude, Math.PI / b)
    } else {
        a.toDouble().pow(1.0 / b.toDouble()).toFloat().decimalFormat()
    }

    fun complexOf(magnitude: Double, phase: Double) = if (magnitude.isFinite())
        Complex.polar(magnitude = magnitude, angle = phase).toString()
    else
        magnitude.toFloat().decimalFormat()
}
