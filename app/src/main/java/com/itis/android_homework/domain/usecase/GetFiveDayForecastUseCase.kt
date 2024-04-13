package com.itis.android_homework.domain.usecase

import com.itis.android_homework.domain.mapper.FiveDayForecastUiModelMapper
import com.itis.android_homework.domain.repository.WeatherRepository
import com.itis.android_homework.presentation.model.FiveDayForecastUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetFiveDayForecastUseCase @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val repository: WeatherRepository,
    private val mapper: FiveDayForecastUiModelMapper,
) {

    suspend operator fun invoke(longitude: Float, latitude: Float): FiveDayForecastUiModel {
        return withContext(dispatcher) {
            val cityWeatherData = repository.get5DayForecast(longitude, latitude)
            mapper.mapDomainToUiModel(cityWeatherData)
        }
    }
}