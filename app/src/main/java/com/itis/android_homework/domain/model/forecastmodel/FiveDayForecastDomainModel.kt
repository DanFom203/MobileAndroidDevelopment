package com.itis.android_homework.domain.model.forecastmodel

data class FiveDayForecastDomainModel (
    val list: List<CityWeatherForecastDomainModel>,
    val cityData: CityDomainModel
)

fun FiveDayForecastDomainModel.isEmptyResponse(): Boolean {
    val isCityDataEmpty = with(this.cityData) {
        cityName == "" && country == ""
    }
    return list.isEmpty() && isCityDataEmpty
}

