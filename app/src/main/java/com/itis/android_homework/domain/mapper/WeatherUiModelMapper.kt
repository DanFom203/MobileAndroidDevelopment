package com.itis.android_homework.domain.mapper

import com.itis.android_homework.domain.model.weathermodel.WeatherDomainModel
import com.itis.android_homework.presentation.model.WeatherCoordUiModel
import com.itis.android_homework.presentation.model.WeatherIconUiModel
import com.itis.android_homework.presentation.model.WeatherMainUiModel
import com.itis.android_homework.presentation.model.WeatherUiModel
import javax.inject.Inject

class WeatherUiModelMapper @Inject constructor() {

    fun mapDomainToUiModel(input: WeatherDomainModel): WeatherUiModel {
        with(input) {
            return WeatherUiModel(
                coordsData = WeatherCoordUiModel(
                    longitude = coordinatesData.longitude,
                    latitude = coordinatesData.latitude,
                ),
                mainData = WeatherMainUiModel(
                    temperature = mainData.temperature,
                    minTemperature = mainData.minTemp,
                    feelsLike = mainData.feelsLike
                ),
                iconData = WeatherIconUiModel(
                    icon = iconData.icon
                ),
                name = name
            )
        }
    }
}