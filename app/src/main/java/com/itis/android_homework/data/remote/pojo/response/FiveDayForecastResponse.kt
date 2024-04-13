package com.itis.android_homework.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

class FiveDayForecastResponse (
    @SerializedName("list")
    val list: List<WeatherData>? = null,
    @SerializedName("city")
    val cityData: CityData? = null
)

class WeatherData(
    @SerializedName("main")
    val main: MainCitiesData? = null,
    @SerializedName("dt_txt")
    val dateText: String? = null
)

class MainCitiesData (
    @SerializedName("temp")
    val temp: Float? = null,
    @SerializedName("feels_like")
    val feelsLike: Float? = null,
    @SerializedName("temp_min")
    val minTemp: Float? = null,
    @SerializedName("temp_max")
    val maxTemp: Float? = null
)

class CityData(
    @SerializedName("name")
    val cityName: String? = null,
    @SerializedName("country")
    val country: String? = null
)