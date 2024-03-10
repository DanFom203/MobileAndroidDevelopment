package com.itis.android_homework.domain.repository

import com.itis.android_homework.domain.model.WeatherDomainModel

interface WeatherRepository {

    suspend fun getCurrentWeatherByCityName(city: String): WeatherDomainModel

}