package com.plcoding.testingcourse.part4.presentation

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

class FirebaseAnalyticsLogger(
    private val analytics: FirebaseAnalytics = Firebase.analytics
): AnalyticsLogger {
    override fun logEvent(key: String, vararg params: LogParam<Any>) {
        analytics.logEvent("save_profile") {
            params.forEach {parameter ->
                param(parameter.key, parameter.value.toString())
            }
        }
    }
}