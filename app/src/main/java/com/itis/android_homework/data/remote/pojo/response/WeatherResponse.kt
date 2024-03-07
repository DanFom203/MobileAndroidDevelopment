package com.itis.android_homework.data.remote.pojo.response

import com.google.gson.annotations.SerializedName

class WeatherResponse(
    @SerializedName("main")
    val mainData: MainData? = null,
    @SerializedName("weather")
    val weatherData: List<IconData>? = null,
)

class MainData(
    @SerializedName("temp")
    val temperature: Float? = null,
    @SerializedName("feels_like")
    val feelsLike: Float? = null,
    @SerializedName("temp_min")
    val minTemp: Float? = null
)

class IconData(
    @SerializedName("icon")
    val icon: String? = null
)