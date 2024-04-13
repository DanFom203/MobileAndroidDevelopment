package com.itis.android_homework.data.mapper

import com.itis.android_homework.base.Constants
import com.itis.android_homework.data.remote.pojo.response.WeatherResponse
import com.itis.android_homework.domain.model.weathermodel.WeatherCoordDomainModel
import com.itis.android_homework.domain.model.weathermodel.WeatherDomainModel
import com.itis.android_homework.domain.model.weathermodel.WeatherIconDomainModel
import com.itis.android_homework.domain.model.weathermodel.WeatherMainDomainModel
import javax.inject.Inject

class WeatherDomainModelMapper @Inject constructor() {

    fun mapResponseToDomainModel(input: WeatherResponse?): WeatherDomainModel? {
        return input?.let {
            WeatherDomainModel(
                coordinatesData = WeatherCoordDomainModel(
                    latitude = it.coordData?.latitude ?: Constants.EMPTY_FLOAT_DATA,
                    longitude = it.coordData?.longitude ?: Constants.EMPTY_FLOAT_DATA,
                ),
                mainData = WeatherMainDomainModel(
                    temperature = it.mainData?.temperature ?: 0f,
                    feelsLike = it.mainData?.feelsLike ?: 0f,
                    minTemp = it.mainData?.minTemp ?: 0f
                ),
                iconData = WeatherIconDomainModel(
                    icon = it.weatherData?.get(0)?.icon ?: ""
                ),
                name = it.name ?: ""
            )
        }
    }
}