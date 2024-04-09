package com.itis.android_homework.domain.usecase

import com.itis.android_homework.domain.mapper.WeatherUiModelMapper
import com.itis.android_homework.domain.repository.WeatherRepository
import com.itis.android_homework.presentation.model.WeatherUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository,
    private val mapper: WeatherUiModelMapper,
) {

    suspend operator fun invoke(cities: List<String>): List<WeatherUiModel> {
//        val citiesWeather: List<WeatherUiModel>
//        withContext(dispatcher) {
//            val weatherData = repository.getCurrentWeatherByCityName(city = city)
//            (mapper.mapDomainToUiModel(weatherData))
//        }
        val citiesWeather = mutableListOf<WeatherUiModel>()

        withContext(dispatcher) {
            cities.forEach { city ->
                val weatherData = repository.getCurrentWeatherByCityName(city = city)
                val weatherUiModel = mapper.mapDomainToUiModel(weatherData)
                citiesWeather.add(weatherUiModel)
            }
        }

        return citiesWeather
    }

}