package com.payamgr.androidbenchmark.util

import androidx.test.platform.app.InstrumentationRegistry

val app get() = InstrumentationRegistry.getInstrumentation().targetContext
