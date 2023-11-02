package com.payamgr.androidbenchmark.data.state

import androidx.test.filters.SmallTest
import com.payamgr.androidbenchmark.data.model.Operation
import com.payamgr.androidbenchmark.util.forEachThis
import org.assertj.core.api.Assertions.*
import org.junit.Test

@SmallTest
class CalculatorStateTest {
    data class Data(val a: Int, val b: Int, val result: String)

    @Test
    fun `addition test`() {
        listOf(
            Data(-1, -1, "-2"),
            Data(-1, 0, "-1"),
            Data(-1, 1, "0"),

            Data(0, -1, "-1"),
            Data(0, 0, "0"),
            Data(0, 1, "1"),

            Data(1, -1, "0"),
            Data(1, 0, "1"),
            Data(1, 1, "2"),

            Data(1000, 1000, "2,000"),
        ).forEachThis {
            val state = CalculatorState(a, b, Operation.Add)
            assertThat(state.result).`as`("(%s) + (%s)", a, b).isEqualTo(result)
        }
    }

    @Test
    fun `subtraction test`() {
        listOf(
            Data(-1, -1, "0"),
            Data(-1, 0, "-1"),
            Data(-1, 1, "-2"),

            Data(0, -1, "1"),
            Data(0, 0, "0"),
            Data(0, 1, "-1"),

            Data(1, -1, "2"),
            Data(1, 0, "1"),
            Data(1, 1, "0"),

            Data(1000, -1000, "2,000"),
        ).forEachThis {
            val state = CalculatorState(a, b, Operation.Sub)
            assertThat(state.result).`as`("(%s) - (%s)", a, b).isEqualTo(result)
        }
    }

    @Test
    fun `multiplication test`() {
        listOf(
            Data(-1, -1, "1"),
            Data(-1, 0, "0"),
            Data(-1, 1, "-1"),

            Data(0, -1, "0"),
            Data(0, 0, "0"),
            Data(0, 1, "0"),

            Data(1, -1, "-1"),
            Data(1, 0, "0"),
            Data(1, 1, "1"),

            Data(100, 100, "10,000"),
        ).forEachThis {
            val state = CalculatorState(a, b, Operation.Mul)
            assertThat(state.result).`as`("(%s) * (%s)", a, b).isEqualTo(result)
        }
    }

    @Test
    fun `division test`() {
        listOf(
            Data(-1, -1, "1"),
            Data(-1, 0, "-∞"),
            Data(-1, 1, "-1"),

            Data(0, -1, "-0"),
            Data(0, 0, "NaN"),
            Data(0, 1, "0"),

            Data(1, -1, "-1"),
            Data(1, 0, "∞"),
            Data(1, 1, "1"),

            Data(1, 3, "0.3333"),
            Data(10_000, 3, "3,333.3333"),
        ).forEachThis {
            val state = CalculatorState(a, b, Operation.Div)
//            println("Data($a, $b, \"${state.result}\"),")
            assertThat(state.result).`as`("(%s) / (%s)", a, b).isEqualTo(result)
        }
    }

    @Test
    fun `power test`() {
        listOf(
            Data(-3, -3, "-0.037"),
            Data(-3, -2, "0.1111"),
            Data(-3, -1, "-0.3333"),
            Data(-3, 0, "1"),
            Data(-3, 1, "-3"),
            Data(-3, 2, "9"),
            Data(-3, 3, "-27"),

            Data(-2, -3, "-0.125"),
            Data(-2, -2, "0.25"),
            Data(-2, -1, "-0.5"),
            Data(-2, 0, "1"),
            Data(-2, 1, "-2"),
            Data(-2, 2, "4"),
            Data(-2, 3, "-8"),

            Data(-1, -3, "-1"),
            Data(-1, -2, "1"),
            Data(-1, -1, "-1"),
            Data(-1, 0, "1"),
            Data(-1, 1, "-1"),
            Data(-1, 2, "1"),
            Data(-1, 3, "-1"),

            Data(0, -3, "∞"),
            Data(0, -2, "∞"),
            Data(0, -1, "∞"),
            Data(0, 0, "1"),
            Data(0, 1, "0"),
            Data(0, 2, "0"),
            Data(0, 3, "0"),

            Data(1, -3, "1"),
            Data(1, -2, "1"),
            Data(1, -1, "1"),
            Data(1, 0, "1"),
            Data(1, 1, "1"),
            Data(1, 2, "1"),
            Data(1, 3, "1"),

            Data(2, -3, "0.125"),
            Data(2, -2, "0.25"),
            Data(2, -1, "0.5"),
            Data(2, 0, "1"),
            Data(2, 1, "2"),
            Data(2, 2, "4"),
            Data(2, 3, "8"),

            Data(3, -3, "0.037"),
            Data(3, -2, "0.1111"),
            Data(3, -1, "0.3333"),
            Data(3, 0, "1"),
            Data(3, 1, "3"),
            Data(3, 2, "9"),
            Data(3, 3, "27"),

            Data(-4, 2, "16"),
            Data(-4, -2, "0.0625"),
            Data(-1_000, 3, "-1,000,000,000"),
        ).forEachThis {
            val state = CalculatorState(a, b, Operation.Pow)
//            println("Data($a, $b, \"${state.result}\"),")
            assertThat(state.result).`as`("(%s) ^ (%s)", a, b).isEqualTo(result)
        }
    }

    /**
     * It's fully tested at "RootCalculatorTest.`root test`". Just to make jacoco happy.
     * @see [RootCalculatorTest]
     */
    @Test
    fun `root test`() {
        data class Data(val a: Int, val b: Int, val result: String)
        listOf(
            Data(-3, -3, "0.3467 - 0.6005i"),
            Data(-2, -2, "-0.7071i"),
            Data(-1, -1, "-1"),
            Data(0, 0, "0"),
            Data(1, 1, "1"),
            Data(2, 2, "1.4142"),
            Data(3, 3, "1.4422"),
            Data(-4, 2, "2i"),
            Data(-4, -2, "-0.5i"),
            Data(-1000, 1, "-1,000"),
        ).forEachThis {
            val state = CalculatorState(a, b, Operation.Root)
//            println("Data($a, $b, \"${state.result}\"),")
            assertThat(state.result).`as`("root(%s, %s)", a, b).isEqualTo(result)
        }
    }

    /**
     * Just to make Jacoco happy.
     */
    @Test
    fun `empty constructor test`() {
        assertThat(CalculatorState().result).isEqualTo("0")
    }
}
