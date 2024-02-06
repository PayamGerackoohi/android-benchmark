package com.payamgr.androidbenchmark.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.Mavericks
import com.payamgr.androidbenchmark.data.hilt.provideAppModule
import com.payamgr.androidbenchmark.ui.page.home.Home
import com.payamgr.androidbenchmark.ui.theme.AndroidBenchmarkTheme
import com.payamgr.androidbenchmark.ui.util.isAndroidSdk31plus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    /**
     * It controls how long the splash screen is kept.
     *
     * Normal @Inject provides the object after "super.onCreate", so [provideAppModule] is used.
     */
    private val splashScreenController by lazy { provideAppModule().splashScreenController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isAndroidSdk31plus) setupSplashScreen()
        super.onCreate(savedInstanceState)
        Mavericks.initialize(this)
        setContent {
            AndroidBenchmarkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home.Page(
                        isUsingGoogleSplashScreen = isAndroidSdk31plus,
                        duration = splashScreenController.totalTime,
                        keepSplashScreen = splashScreenController.keep,
                        hideSplashScreen = splashScreenController::hide,
                    )
                }
            }
        }
    }

    private fun setupSplashScreen() {
        installSplashScreen().setKeepOnScreenCondition { splashScreenController.keep }
        lifecycleScope.launch {
            delay(splashScreenController.duration)
            splashScreenController.hide()
        }
    }
}
