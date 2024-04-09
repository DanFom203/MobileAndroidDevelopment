package com.itis.android_homework.presentation.screens.weatherdetails

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import com.itis.android_homework.data.ExceptionHandlerDelegate
import com.itis.android_homework.data.runCatching
import com.itis.android_homework.domain.usecase.GetWeatherDataUseCase
import com.itis.android_homework.presentation.base.BaseViewModel
import com.itis.android_homework.presentation.model.WeatherUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    @Assisted(value = WEATHER_ID_KEY) private val weatherId: String,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(WEATHER_ID_KEY) weatherId: String): WeatherDetailsViewModel
    }

    fun start(arguments: Bundle) {
        arguments.getString("")
    }

    private companion object {
        private const val WEATHER_ID_KEY = "WEATHER_ID"
    }

//    private val _currentWeatherFlow = MutableStateFlow<WeatherUiModel?>(null)
//    val currentWeatherFlow: StateFlow<WeatherUiModel?>
//        get() = _currentWeatherFlow
//
//    val errorsChannel = Channel<Throwable>()
//
//    fun getWeatherInfo(city: String) {
//        viewModelScope.launch {
//            runCatching(exceptionHandlerDelegate) {
//                getWeatherDataUseCase.invoke(city)
//            }.onSuccess {
//                _currentWeatherFlow.value = it
//            }.onFailure {
//                errorsChannel.send(it)
//            }
//        }
//    }
//
//    override fun onCleared() {
//        errorsChannel.close()
//        super.onCleared()
//    }
}