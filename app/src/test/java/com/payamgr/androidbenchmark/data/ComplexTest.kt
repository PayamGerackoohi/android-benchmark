package com.payamgr.androidbenchmark.data

import androidx.test.filters.SmallTest
import com.payamgr.androidbenchmark.util.forEachThis
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

@SmallTest
class ComplexTest {
    data class Data(val c: Complex, val result: String)

    @Test
    fun `toString test`() {
        listOf(
            Data(Complex(-1f, -1f), "-1 - i"),
            Data(Complex(-1f, 0f), "-1"),
            Data(Complex(-1f, 1f), "-1 + i"),

            Data(Complex(0f, -1f), "-i"),
            Data(Complex(0f, 0f), "0"),
            Data(Complex(0f, 1f), "i"),

            Data(Complex(1f, -1f), "1 - i"),
            Data(Complex(1f, 0f), "1"),
            Data(Complex(1f, 1f), "1 + i"),

            Data(Complex(1.2246469e-10f, 2f), "2i"),
            Data(Complex(0f, 2f), "2i"),
            Data(Complex(0f, -0.5f), "-0.5i"),

            Data(Complex(1234.4321f, -5678.8765f), "1,234.4321 - 5,678.8765i"),
        ).forEachThis {
            assertThat(c.toString()).`as`("(%s, %s)", c.x, c.y).isEqualTo(result)
        }
    }

    @Test
    fun `precision test`() {
        listOf(
            Data(Complex(1e-6f, -1e-5f), "0"),
            Data(Complex(0.12345f, 3.45678f), "0.1235 + 3.4568i"),
            Data(Complex(1_234.5f, 3_456.78f), "1,234.5 + 3,456.78i"),
        ).forEachThis {
            assertThat(c.toString()).`as`("(%s, %s, %s)", c.x, c.y, c.precision).isEqualTo(result)
        }
        val precision = 1
        listOf(
            Data(Complex(1e-6f, -1e-4f), "0"),
            Data(Complex(0.1234f, 5.6789f), "0.1 + 5.7i"),
            Data(Complex(1_234f, 5_678.9f), "1,234 + 5,678.9i"),
        ).forEachThis {
            c.precision = precision
            assertThat(c.toString()).`as`("(%s, %s, %s)", c.x, c.y, c.precision).isEqualTo(result)
        }
    }
}