package com.itis.android_homework.data.remote

import com.itis.android_homework.data.remote.pojo.response.FiveDayForecastResponse
import com.itis.android_homework.data.remote.pojo.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeatherByCity(
        @Query(value = "q") city: String,
    ): WeatherResponse?

    @GET("forecast")
    suspend fun get5DayForecast(
        @Query(value = "lon") longitude: Float,
        @Query(value = "lat") latitude: Float
    ): FiveDayForecastResponse?

}