package com.itis.android_homework.presentation.model

data class WeatherUiModel(
    val mainData: WeatherMainUiModel,
    val iconData: WeatherIconUiModel,
    val coordsData: WeatherCoordUiModel,
    val name: String
)

data class WeatherMainUiModel(
    val temperature: Float,
    val minTemperature: Float,
    val feelsLike: Float,
)

data class WeatherIconUiModel(
    val icon: String
)

data class WeatherCoordUiModel(
    val longitude: Float,
    val latitude: Float,
)
