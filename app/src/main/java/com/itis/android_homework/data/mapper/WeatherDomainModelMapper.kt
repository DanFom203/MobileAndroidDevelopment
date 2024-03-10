package com.itis.android_homework.data.mapper

import com.itis.android_homework.data.remote.pojo.response.WeatherResponse
import com.itis.android_homework.domain.model.WeatherDomainModel
import com.itis.android_homework.domain.model.WeatherIconDomainModel
import com.itis.android_homework.domain.model.WeatherMainDomainModel

class WeatherDomainModelMapper {

    fun mapResponseToDomainModel(input: WeatherResponse?): WeatherDomainModel? {
        return input?.let {
            WeatherDomainModel(
                mainData = WeatherMainDomainModel(
                    temperature = it.mainData?.temperature ?: 0f,
                    feelsLike = it.mainData?.feelsLike ?: 0f,
                    minTemp = it.mainData?.minTemp ?: 0f
                ),
                iconData = WeatherIconDomainModel(
                    icon = it.weatherData?.get(0)?.icon ?: ""
                )
            )
        }
    }
}