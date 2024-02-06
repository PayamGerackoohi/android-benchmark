package com.payamgr.androidbenchmark.ui.module

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.payamgr.androidbenchmark.ui.theme.AndroidBenchmarkTheme
import com.payamgr.androidbenchmark.util.Screenshot
import com.payamgr.androidbenchmark.util.take
import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SplashScreenTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun moduleTest() = runTest {
        val onEnd = mockk<() -> Unit>()
        val mutex = Mutex(true)
        var isOnEndCalled = false
        justRun { onEnd() }

        rule.setContent {
            AndroidBenchmarkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SplashScreen.Module(
                        duration = 10,
                        onEnd = {
                            onEnd()
                            isOnEndCalled = true
                        },
                        start = { mutex.lock() }
                    )
                }
            }
        }

        // Verify the initial state
        rule.onNodeWithContentDescription("Splashscreen").isDisplayed()
        rule.onNodeWithTag("SplashScreen.IconBackground").isDisplayed()
        rule.onNodeWithContentDescription("Animated Vector: Splashscreen Icon").isDisplayed()
        Screenshot.Splashscreen.take()

        // - Verify the initial state transition from 'Beginning' to 'End'
        rule.onNode(hasStateDescription("Beginning")).assertExists()
        mutex.unlock()
        rule.onNode(hasStateDescription("End")).assertExists()

        // Verify the 'onEnd' callback is called after 10ms
        rule.waitUntil { isOnEndCalled }
        verify { onEnd() }

        // - The animated vector state is the 'End'
        rule.onNode(hasStateDescription("End")).assertExists()

        confirmVerified()
    }
}
