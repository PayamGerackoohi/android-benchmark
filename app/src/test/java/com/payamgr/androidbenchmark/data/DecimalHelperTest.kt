package com.payamgr.androidbenchmark.data

import androidx.test.filters.SmallTest
import com.payamgr.androidbenchmark.util.forEachThis
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

@SmallTest
class DecimalHelperTest {
    @Test
    fun `decimalPatternOf test`() {
        data class Data(val digits: Int, val result: String)
        listOf(
            Data(-1, ""),
            Data(0, ",###"),
            Data(1, ",###.#"),
            Data(2, ",###.##"),
            Data(3, ",###.###"),
            Data(4, ",###.####"),
            Data(5, ",###.#####"),
            Data(6, ",###.######"),
        ).forEachThis {
            Assertions.assertThat(DecimalHelper.decimalPatternOf(digits))
                .`as`("%s", digits)
                .isEqualTo(result)
        }
    }

    @Test
    fun `epsilonOf test`() {
        data class Data(val precision: Int, val result: Float)
        listOf(
            Data(-3, 1000f),
            Data(-2, 100f),
            Data(-1, 10f),
            Data(0, 1f),
            Data(1, .1f),
            Data(2, .01f),
            Data(3, .001f),
        ).forEachThis {
            Assertions.assertThat(DecimalHelper.epsilonOf(precision))
                .`as`("%s", precision)
                .isEqualTo(result)
        }
    }

    @Test
    fun `isCloseTo test`() {
        data class Data(val a: Float, val b: Float, val result: Boolean)

        val epsilon = 1e-3f
        listOf(
            Data(-1e-2f, 0f, false),
            Data(-1e-4f, 0f, true),
            Data(0f, 0f, true),
            Data(1e-4f, 0f, true),
            Data(1e-2f, 0f, false),

            Data(0.99f, 1f, false),
            Data(0.9999f, 1f, true),
            Data(1f, 1f, true),
            Data(1.0001f, 1f, true),
            Data(1.01f, 1f, false),
        ).forEachThis {
            Assertions.assertThat(a.isCloseTo(b, epsilon))
                .`as`("(%s, %s, %s)", a, b, epsilon)
                .isEqualTo(result)
        }
    }
}
