package com.itis.android_homework.ui.fragments.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationSettingsViewModel : ViewModel() {
    val bundleData = MutableLiveData<Bundle>()

    private val _airplaneMode = MutableLiveData<Boolean>()
    val airplaneMode: LiveData<Boolean> get() = _airplaneMode

    fun updateAirplaneMode(isAirplaneModeOn: Boolean) {
        _airplaneMode.value = isAirplaneModeOn
    }
}