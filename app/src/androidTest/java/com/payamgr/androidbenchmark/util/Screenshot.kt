package com.payamgr.androidbenchmark.util

import android.graphics.Bitmap.CompressFormat

//@Suppress("EnumEntryName")
enum class Screenshot {
    Splashscreen,
    Calculator,
}

fun Screenshot.take(
    fileExtensions: String = "webp",
    compressFormat: CompressFormat = CompressFormat.WEBP_LOSSY,
    qualityPercentage: Int = 85,
    minDimension: Int = 350,
) = takeScreenshot(
    this,
    fileExtensions,
    compressFormat,
    qualityPercentage,
    minDimension,
)
