package com.itis.android_homework.domain.repository

import com.itis.android_homework.domain.model.forecastmodel.FiveDayForecastDomainModel
import com.itis.android_homework.domain.model.weathermodel.WeatherDomainModel

interface WeatherRepository {

    suspend fun getCurrentWeatherByCityName(city: String): WeatherDomainModel
    suspend fun get5DayForecast(longitude: Float, latitude: Float): FiveDayForecastDomainModel

}