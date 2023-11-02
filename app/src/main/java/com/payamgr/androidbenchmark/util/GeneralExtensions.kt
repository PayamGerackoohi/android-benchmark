package com.payamgr.androidbenchmark.util

// todo Jacoco does not recognize kotlin 'inline' functions. Migrate to 'Kover'?
//inline fun <T> Iterable<T>.forEachThis(action: T.() -> Unit) = forEach { it.action() }
fun <T> Iterable<T>.forEachThis(action: T.() -> Unit) = forEach { it.action() }
