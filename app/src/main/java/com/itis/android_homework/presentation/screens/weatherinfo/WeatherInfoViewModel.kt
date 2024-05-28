package com.itis.android_homework.presentation.screens.weatherinfo

import androidx.lifecycle.viewModelScope
import com.itis.android_homework.data.ExceptionHandlerDelegate
import com.itis.android_homework.data.runCatching
import com.itis.android_homework.domain.usecase.GetWeatherDataUseCase
import com.itis.android_homework.presentation.base.BaseViewModel
import com.itis.android_homework.presentation.model.WeatherUiModel
import com.itis.android_homework.utils.CitiesRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherInfoViewModel @Inject constructor(
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
) : BaseViewModel() {

    private val cities = CitiesRepository.citiesList

    private val _currentWeatherFlow = MutableStateFlow<List<WeatherUiModel>?>(null)
    val currentWeatherFlow: StateFlow<List<WeatherUiModel>?>
        get() = _currentWeatherFlow

    val errorsChannel = Channel<Throwable>()

    private var weatherUpdateJob: Job? = null

    init {
        startWeatherUpdates()
    }

    private fun startWeatherUpdates() {
        weatherUpdateJob?.cancel()
        weatherUpdateJob = viewModelScope.launch {
            updateWeatherInfo()
            while (true) {
                delay(TEN_MINUTES)
                updateWeatherInfo()
            }
        }
    }

    private suspend fun updateWeatherInfo() {
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
        weatherUpdateJob?.cancel()
        super.onCleared()
    }

    companion object {
        const val TEN_MINUTES: Long = 10 * 60 * 1000
    }
}