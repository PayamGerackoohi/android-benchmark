package com.payamgr.androidbenchmark.data.controller

interface SplashScreenController {
    val delay: Long
    val duration: Long
    val totalTime: Long
    val keep: Boolean
    fun hide()
}
