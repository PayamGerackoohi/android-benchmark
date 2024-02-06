package com.payamgr.androidbenchmark.ui.page.home

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.airbnb.mvrx.mocking.MockableMavericks
import com.payamgr.androidbenchmark.util.app
import io.mockk.confirmVerified
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.sync.Mutex
import org.junit.Rule
import org.junit.Test

class HomeTest {
    @get:Rule
    val rule = createComposeRule()

    @Test
    fun page_usingTheGoogleSplashScreen_shouldShowTheCalculatorPage() {
        rule.setContent {
            Home.Page(
                isUsingGoogleSplashScreen = true,
                duration = 0L,
                keepSplashScreen = false,
                hideSplashScreen = {},
            )
        }

        // Verify the initial state
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()
        rule.onAllNodesWithContentDescription("Splashscreen").assertCountEquals(0)
    }

    @Test
    fun page_notUsingTheGoogleSplashScreen_and_notKeepSplashScreen_shouldShowSkipTheCustomSplashscreen() {
        rule.setContent {
            Home.Page(
                isUsingGoogleSplashScreen = true,
                duration = 0L,
                keepSplashScreen = false,
                hideSplashScreen = {},
            )
        }

        // Verify the initial state
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()
        rule.onAllNodesWithContentDescription("Splashscreen").assertCountEquals(0)
    }

    @Test
    fun page_notUsingTheGoogleSplashScreen_shouldShowTheCustomSplashscreen_and_TransitionToTheCalculatorPage() {
        val animationMutex = Mutex(true)
        val hideSplashScreen = mockk<() -> Unit>()
        justRun { hideSplashScreen() }

        MockableMavericks.initialize(app)
        rule.setContent {
            Home.Page(
                isUsingGoogleSplashScreen = false,
                duration = 10L,
                keepSplashScreen = true,
                hideSplashScreen = hideSplashScreen,
                animationStart = { animationMutex.lock() },
            )
        }

        // Verify the initial state
        rule.onNodeWithContentDescription("Splashscreen").assertIsDisplayed()
        rule.onAllNodesWithTag("CalculatorView").assertCountEquals(0)

        // Wait until the end of the animation
        animationMutex.unlock()
        rule.waitUntil {
            rule.onAllNodesWithTag("CalculatorView").fetchSemanticsNodes().isNotEmpty()
        }

        // Verify the transition to the 'Calculator' page
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()
        rule.onAllNodesWithContentDescription("Splashscreen").assertCountEquals(0)
        verify { hideSplashScreen() }

        confirmVerified()
    }

    @Test
    fun content_isSplashMode_shouldShowTheSplashscreen() {
        var isHideSplashCalled = false
        val hideSplash = mockk<() -> Unit>()
        justRun { hideSplash() }

        rule.setContent {
            Home.Content(
                duration = 10L,
                isSplash = true,
                hideSplash = {
                    hideSplash()
                    isHideSplashCalled = true
                },
            )
        }

        // Verify the initial state
        rule.onNodeWithContentDescription("Splashscreen").assertIsDisplayed()
        rule.onAllNodesWithTag("CalculatorView").assertCountEquals(0)

        // Wait until the end of the animation
        rule.waitUntil { isHideSplashCalled }

        // Verify the 'hideSplash' callback is called
        verify { hideSplash() }

        confirmVerified()
    }

    @Test
    fun content_notSplashMode_shouldShowTheCalculatorPage() {
        rule.setContent {
            Home.Content(
                duration = 0L,
                isSplash = false,
                hideSplash = {},
            )
        }

        // Verify the initial state
        rule.onNodeWithTag("CalculatorView").assertIsDisplayed()
        rule.onAllNodesWithContentDescription("Splashscreen").assertCountEquals(0)
    }
}
