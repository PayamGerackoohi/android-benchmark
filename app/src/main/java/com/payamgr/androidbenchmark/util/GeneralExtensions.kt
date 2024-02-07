package com.payamgr.androidbenchmark.util

inline fun <T> Iterable<T>.forEachThis(action: T.() -> Unit) = forEach { it.action() }
