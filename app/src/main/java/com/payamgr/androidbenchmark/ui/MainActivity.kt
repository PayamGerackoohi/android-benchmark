package com.payamgr.androidbenchmark.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.airbnb.mvrx.Mavericks
import com.payamgr.androidbenchmark.ui.page.CalculatorView
import com.payamgr.androidbenchmark.ui.theme.AndroidBenchmarkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mavericks.initialize(this)
        setContent {
            AndroidBenchmarkTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    CalculatorView.Page()
                }
            }
        }
    }
}
