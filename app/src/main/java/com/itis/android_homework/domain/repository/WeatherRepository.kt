package com.itis.android_homework.domain.repository

import com.itis.android_homework.domain.model.WeatherDomainModel

interface WeatherRepository {

    suspend fun getCurrentWeatherByCityName(city: String): WeatherDomainModel
    suspend fun getDailyForecast16(query: String): Any

}