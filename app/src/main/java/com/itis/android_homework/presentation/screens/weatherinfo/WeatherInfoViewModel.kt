package com.itis.android_homework.presentation.screens.weatherinfo

import androidx.lifecycle.viewModelScope
import com.itis.android_homework.data.ExceptionHandlerDelegate
import com.itis.android_homework.data.runCatching
import com.itis.android_homework.domain.usecase.GetWeatherDataUseCase
import com.itis.android_homework.presentation.base.BaseViewModel
import com.itis.android_homework.presentation.model.WeatherUiModel
import com.itis.android_homework.utils.CitiesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
) : BaseViewModel() {

    private val _currentWeatherFlow = MutableStateFlow<List<WeatherUiModel>?>(null)
    val currentWeatherFlow: StateFlow<List<WeatherUiModel>?>
        get() = _currentWeatherFlow

    val errorsChannel = Channel<Throwable>()

    fun getWeatherInfo(cities: List<String>) {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                getWeatherDataUseCase.invoke(cities)
            }.onSuccess {
                _currentWeatherFlow.value = it
            }.onFailure {
                errorsChannel.send(it)
            }
        }
    }

    override fun onCleared() {
        errorsChannel.close()
        super.onCleared()
    }
}