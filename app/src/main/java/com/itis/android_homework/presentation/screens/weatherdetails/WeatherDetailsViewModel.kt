package com.itis.android_homework.presentation.screens.weatherdetails

import androidx.lifecycle.viewModelScope
import com.itis.android_homework.base.Keys
import com.itis.android_homework.data.ExceptionHandlerDelegate
import com.itis.android_homework.data.runCatching
import com.itis.android_homework.domain.usecase.GetFiveDayForecastUseCase
import com.itis.android_homework.presentation.base.BaseViewModel
import com.itis.android_homework.presentation.model.CityAllData
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    private val get5DayForecastUseCase: GetFiveDayForecastUseCase,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
    @Assisted(value = Keys.WEATHER_ICON_KEY) private val weatherIcon: String,
    @Assisted(value = Keys.CITY_LONG_KEY) private val weatherLong: Float,
    @Assisted(value = Keys.CITY_LAT_KEY) private val weatherLat: Float,
    @Assisted(value = Keys.CITY_TEMPERATURE_KEY) private val cityTemperature: Float
) : BaseViewModel() {

    private val _cityWeatherFlow = MutableStateFlow<CityAllData?>(null)
    val cityWeatherFlow: StateFlow<CityAllData?>
        get() = _cityWeatherFlow

    val errorsChannel = Channel<Throwable>()

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(Keys.WEATHER_ICON_KEY) weatherIcon: String,
                   @Assisted(Keys.CITY_LONG_KEY) weatherLong: Float,
                   @Assisted(Keys.CITY_LAT_KEY) weatherLat: Float,
                   @Assisted(value = Keys.CITY_TEMPERATURE_KEY) cityTemperature: Float
        ): WeatherDetailsViewModel
    }

    fun getWeatherInfo() {
        viewModelScope.launch {
            runCatching(exceptionHandlerDelegate) {
                get5DayForecastUseCase.invoke(weatherLong, weatherLat)
            }.onSuccess {

                _cityWeatherFlow.value = CityAllData(
                    it.list, it.cityData, cityTemperature, weatherIcon
                )
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