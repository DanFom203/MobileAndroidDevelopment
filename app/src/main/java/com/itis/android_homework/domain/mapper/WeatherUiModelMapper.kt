package com.itis.android_homework.domain.mapper

import com.itis.android_homework.domain.model.WeatherDomainModel
import com.itis.android_homework.presentation.model.WeatherIconUiModel
import com.itis.android_homework.presentation.model.WeatherMainUiModel
import com.itis.android_homework.presentation.model.WeatherUiModel

class WeatherUiModelMapper {

    fun mapDomainToUiModel(input: WeatherDomainModel): WeatherUiModel {
        with(input) {
            return WeatherUiModel(
                mainData = WeatherMainUiModel(
                    temperature = mainData.temperature,
                    minTemperature = mainData.minTemp,
                    feelsLike = mainData.feelsLike
                ),
                iconData = WeatherIconUiModel(
                    icon = iconData.icon
                )
            )
        }
    }
}