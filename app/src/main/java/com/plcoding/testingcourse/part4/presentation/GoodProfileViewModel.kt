package com.plcoding.testingcourse.part4.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.plcoding.testingcourse.R

class GoodProfileViewModel(
    private val analytics: AnalyticsLogger
) : ViewModel() {

    var state by mutableStateOf(GoodProfileState())
        private set

    fun saveProfile() {
        analytics.logEvent(
            "save_profile",
            LogParam("profile_id", state.profileId),
            LogParam("username", state.username),
        )
        state = state.copy(
            infoMessage = UiText.StringResource(R.string.successfully_saved_profile)
        )
    }
}