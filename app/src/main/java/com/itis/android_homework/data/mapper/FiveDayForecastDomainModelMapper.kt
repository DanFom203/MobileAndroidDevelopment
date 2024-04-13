package com.itis.android_homework.data.mapper

import com.itis.android_homework.base.Constants
import com.itis.android_homework.data.remote.pojo.response.FiveDayForecastResponse
import com.itis.android_homework.domain.model.forecastmodel.CityDomainModel
import com.itis.android_homework.domain.model.forecastmodel.CityWeatherForecastDomainModel
import com.itis.android_homework.domain.model.forecastmodel.FiveDayForecastDomainModel
import com.itis.android_homework.domain.model.forecastmodel.Main
import javax.inject.Inject

class FiveDayForecastDomainModelMapper @Inject constructor() {

    fun mapResponseToDomainModel(input: FiveDayForecastResponse?): FiveDayForecastDomainModel? {
        val citiesWeather = mutableListOf<CityWeatherForecastDomainModel>()
        var fiveDayForecast: FiveDayForecastDomainModel? = null
        input?.let {
            it.list?.forEach { weatherData ->
                val cityWeather = CityWeatherForecastDomainModel(
                    main = Main(
                        temp = weatherData.main?.temp ?: Constants.EMPTY_FLOAT_DATA,
                        feelsLike = weatherData.main?.feelsLike ?: Constants.EMPTY_FLOAT_DATA,
                        minTemp = weatherData.main?.minTemp ?: Constants.EMPTY_FLOAT_DATA,
                        maxTemp = weatherData.main?.maxTemp ?: Constants.EMPTY_FLOAT_DATA
                    ),
                    dateText = weatherData.dateText ?: ""
                )
                citiesWeather.add(cityWeather)
            }

            fiveDayForecast = FiveDayForecastDomainModel(
                list = citiesWeather,
                cityData = CityDomainModel(
                    cityName = it.cityData?.cityName ?: "",
                    country = it.cityData?.country ?: ""
                )
            )
        }
        return fiveDayForecast
    }
}