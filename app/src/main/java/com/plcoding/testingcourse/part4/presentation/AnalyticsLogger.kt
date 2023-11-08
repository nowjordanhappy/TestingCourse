package com.plcoding.testingcourse.part4.presentation

interface AnalyticsLogger {
    fun logEvent(key: String, vararg  params: LogParam<Any>)
}

data class LogParam<T>(
    val key: String,
    val value: T
)