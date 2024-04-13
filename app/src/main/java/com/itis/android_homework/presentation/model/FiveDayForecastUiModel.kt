package com.itis.android_homework.presentation.model

data class FiveDayForecastUiModel (
    val list: List<CityWeatherForecastUiModel>,
    val cityData: CityUiModel
)

data class CityWeatherForecastUiModel(
    val main: Main,
    val dateText: String
)

data class Main(
    val temp: Float,
    val feelsLike: Float,
    val minTemp: Float,
    val maxTemp: Float
)

data class CityUiModel(
    val cityName: String,
    val country: String
)

data class CityAllData(
    val list: List<CityWeatherForecastUiModel>,
    val cityData: CityUiModel,
    val temperature: Float,
    val icon: String
)
