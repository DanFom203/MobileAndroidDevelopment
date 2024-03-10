package com.itis.android_homework.presentation.model

data class WeatherUiModel(
    val mainData: WeatherMainUiModel,
    val iconData: WeatherIconUiModel
)

data class WeatherMainUiModel(
    val temperature: Float,
    val minTemperature: Float,
    val feelsLike: Float,
)

data class WeatherIconUiModel(
    val icon: String
)
