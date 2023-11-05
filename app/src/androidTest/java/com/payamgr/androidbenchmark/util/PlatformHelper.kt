package com.payamgr.androidbenchmark.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry

val app: Context get() = InstrumentationRegistry.getInstrumentation().targetContext
