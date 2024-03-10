package com.itis.android_homework.domain.model

data class WeatherDomainModel(
    val mainData: WeatherMainDomainModel,
    val iconData: WeatherIconDomainModel
)

fun WeatherDomainModel.isEmptyResponse(): Boolean {
    val isMainDataEmpty = with(this.mainData) {
        temperature == 0f && minTemp == 0f && feelsLike == 0f
    }
    val isIconDataEmpty = with(this.iconData) {
        icon == ""
    }
    return isMainDataEmpty && isIconDataEmpty
}

