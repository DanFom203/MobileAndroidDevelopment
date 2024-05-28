package com.itis.android_homework.domain.mapper

import com.itis.android_homework.domain.model.forecastmodel.FiveDayForecastDomainModel
import com.itis.android_homework.presentation.model.CityUiModel
import com.itis.android_homework.presentation.model.CityWeatherForecastUiModel
import com.itis.android_homework.presentation.model.FiveDayForecastUiModel
import com.itis.android_homework.presentation.model.Main
import javax.inject.Inject

class FiveDayForecastUiModelMapper @Inject constructor() {

    fun mapDomainToUiModel(input: FiveDayForecastDomainModel): FiveDayForecastUiModel {
        val citiesWeather = mutableListOf<CityWeatherForecastUiModel>()
        input.let {
            it.list.forEach { weatherData ->
                val cityWeather = CityWeatherForecastUiModel(

                    main = Main(
                        temp = weatherData.main.temp,
                        feelsLike = weatherData.main.feelsLike,
                        minTemp = weatherData.main.minTemp,
                        maxTemp = weatherData.main.maxTemp
                    ),
                    dateText = weatherData.dateText
                )
                citiesWeather.add(cityWeather)
            }
            return FiveDayForecastUiModel(
                list = citiesWeather,
                cityData = CityUiModel(
                    cityName = it.cityData.cityName,
                    country = it.cityData.country
                )
            )
        }
    }
}