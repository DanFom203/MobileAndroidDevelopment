package com.itis.android_homework.domain.model.forecastmodel

data class CityWeatherForecastDomainModel(
    val main: Main,
    val dateText: String
)

data class Main(
    val temp: Float,
    val feelsLike: Float,
    val minTemp: Float,
    val maxTemp: Float
)