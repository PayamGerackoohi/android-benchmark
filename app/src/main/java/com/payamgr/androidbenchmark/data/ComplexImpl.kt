package com.payamgr.androidbenchmark.data

import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class ComplexImpl(x: Float, y: Float) : Complex(x, y) {
    companion object {
        fun polar(magnitude: Float, phase: Float): Complex = ComplexImpl(
            magnitude * cos(phase),
            magnitude * sin(phase),
        )
    }

    override var precision: Int = DecimalHelper.DefaultPrecision
        set(value) {
            field = value
            pattern = DecimalHelper.decimalPatternOf(field)
            epsilon = DecimalHelper.epsilonOf(precision)
        }
    private var pattern: String = DecimalHelper.decimalPatternOf(precision)
    private var epsilon = DecimalHelper.epsilonOf(precision)

    override fun toString(): String {
        return if (x.isCloseTo(0f, epsilon)) {
            if (y.isCloseTo(0f, epsilon)) "0"
            else "${format(y).removeOne()}i"
        } else {
            val i = if (y.isCloseTo(0f, epsilon)) ""
            else " ${if (y > 0) "+" else "-"} ${format(abs(y)).removeOne()}i"
            "${format(x)}$i"
        }
    }

    private fun String.removeOne() = when (this) {
        "1" -> ""
        "-1" -> "-"
        else -> this
    }

    private fun format(value: Float) = DecimalFormat(pattern).format(value)
}
