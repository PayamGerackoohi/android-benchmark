package com.payamgr.androidbenchmark.data

import androidx.test.filters.SmallTest
import com.payamgr.androidbenchmark.util.forEachThis
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

@SmallTest
class RootCalculatorTest {
    @Test
    fun `root test`() {
        data class Data(val a: Int, val b: Int, val result: String)
        listOf(
            Data(-3, -3, "0.3467 - 0.6005i"),
            Data(-3, -2, "-0.5774i"),
            Data(-3, -1, "-0.3333"),
            Data(-3, 0, "NaN"),
            Data(-3, 1, "-3"),
            Data(-3, 2, "1.7321i"),
            Data(-3, 3, "0.7211 + 1.249i"),

            Data(-2, -3, "0.3969 - 0.6874i"),
            Data(-2, -2, "-0.7071i"),
            Data(-2, -1, "-0.5"),
            Data(-2, 0, "NaN"),
            Data(-2, 1, "-2"),
            Data(-2, 2, "1.4142i"),
            Data(-2, 3, "0.63 + 1.0911i"),

            Data(-1, -3, "0.5 - 0.866i"),
            Data(-1, -2, "-i"),
            Data(-1, -1, "-1"),
            Data(-1, 0, "NaN"),
            Data(-1, 1, "-1"),
            Data(-1, 2, "i"),
            Data(-1, 3, "0.5 + 0.866i"),

            Data(0, -3, "∞"),
            Data(0, -2, "∞"),
            Data(0, -1, "∞"),
            Data(0, 0, "0"),
            Data(0, 1, "0"),
            Data(0, 2, "0"),
            Data(0, 3, "0"),

            Data(1, -3, "1"),
            Data(1, -2, "1"),
            Data(1, -1, "1"),
            Data(1, 0, "NaN"),
            Data(1, 1, "1"),
            Data(1, 2, "1"),
            Data(1, 3, "1"),

            Data(2, -3, "0.7937"),
            Data(2, -2, "0.7071"),
            Data(2, -1, "0.5"),
            Data(2, 0, "∞"),
            Data(2, 1, "2"),
            Data(2, 2, "1.4142"),
            Data(2, 3, "1.2599"),

            Data(3, -3, "0.6934"),
            Data(3, -2, "0.5774"),
            Data(3, -1, "0.3333"),
            Data(3, 0, "∞"),
            Data(3, 1, "3"),
            Data(3, 2, "1.7321"),
            Data(3, 3, "1.4422"),

            Data(-4, 2, "2i"),
            Data(-4, -2, "-0.5i"),
            Data(-1000, 1, "-1,000"),
        ).forEachThis {
//            println("Data($a, $b, \"${RootCalculator.root(a, b)}\"),")
            Assertions.assertThat(RootCalculator.root(a, b))
                .`as`("%s", this)
                .isEqualTo(result)
        }
    }

    @Test
    fun root() {
        data class Data(val magnitude: Double, val phase: Double, val result: String)
        listOf(
            Data(0.0, 0.0, "0"),
            Data(Double.POSITIVE_INFINITY, 0.0, "∞"),
            Data(Double.NEGATIVE_INFINITY, 0.0, "-∞"),
        ).forEachThis {
//            println("Data($magnitude, $phase, \"${RootCalculator.complexOf(magnitude, phase)}\"),")
            Assertions.assertThat(RootCalculator.complexOf(magnitude, phase))
                .`as`("%s", this)
                .isEqualTo(result)
        }
    }
}
