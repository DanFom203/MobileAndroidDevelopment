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

    suspend operator fun invoke(city: String): WeatherUiModel {
        return withContext(dispatcher) {
            val weatherData = repository.getCurrentWeatherByCityName(city = city)
            mapper.mapDomainToUiModel(weatherData)
        }
    }

}