package com.itis.android_homework.data.remote

import com.itis.android_homework.data.ExceptionHandlerDelegate
import com.itis.android_homework.data.remote.pojo.response.WeatherResponse
import com.itis.android_homework.data.runCatching

class OpenWeatherApiDecorator(
    private val openWeatherApi: OpenWeatherApi,
    private val exceptionHandlerDelegate: ExceptionHandlerDelegate,
) {

    suspend fun getCurrentWeatherByCity(city: String): Result<WeatherResponse?> {
        return runCatching(exceptionHandlerDelegate) {
            openWeatherApi.getCurrentWeatherByCity(city)
        }
    }
}