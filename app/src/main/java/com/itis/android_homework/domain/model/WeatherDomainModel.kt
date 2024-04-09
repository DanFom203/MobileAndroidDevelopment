package com.itis.android_homework.domain.model

import com.itis.android_homework.base.Constants

data class WeatherDomainModel(
    val coordinatesData: WeatherCoordDomainModel,
    val mainData: WeatherMainDomainModel,
    val iconData: WeatherIconDomainModel,
    val name: String
)

fun WeatherDomainModel.isEmptyResponse(): Boolean {
    val isMainDataEmpty = with(this.mainData) {
        temperature == 0f && minTemp == 0f && feelsLike == 0f
    }
    val isIconDataEmpty = with(this.iconData) {
        icon == ""
    }
    val isCoordDataEmpty = with(this.coordinatesData) {
        longitude == Constants.EMPTY_FLOAT_DATA && latitude == Constants.EMPTY_FLOAT_DATA
    }
    val isNameEmpty = name == ""
    return isMainDataEmpty && isIconDataEmpty && isCoordDataEmpty && isNameEmpty
}

