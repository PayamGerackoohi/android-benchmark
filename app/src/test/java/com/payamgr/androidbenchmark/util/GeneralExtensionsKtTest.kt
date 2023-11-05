package com.payamgr.androidbenchmark.util

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class GeneralExtensionsKtTest {
    @Test
    fun `forEachThis test`() {
        val list = listOf(2, 3, 5, 7)
        val result = buildString {
            append("[")
            var isFirst = true
            list.forEachThis {
                if (isFirst)
                    isFirst = false
                else
                    append(", ")
                append(this)
            }
            append("]")
        }
        assertThat(result).isEqualTo("[2, 3, 5, 7]")
    }
}
