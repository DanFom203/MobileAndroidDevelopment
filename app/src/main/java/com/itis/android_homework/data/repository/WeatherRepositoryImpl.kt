package com.itis.android_homework.data.repository


import com.itis.android_homework.R
import com.itis.android_homework.data.exceptions.EmptyWeatherResponseException
import com.itis.android_homework.data.mapper.WeatherDomainModelMapper
import com.itis.android_homework.domain.repository.WeatherRepository
import com.itis.android_homework.data.remote.OpenWeatherApi
import com.itis.android_homework.domain.model.WeatherDomainModel
import com.itis.android_homework.domain.model.isEmptyResponse
import com.itis.android_homework.utils.ResManagerImpl
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val domainModelMapper: WeatherDomainModelMapper,
    private val resManager: ResManagerImpl,
) : WeatherRepository {

    override suspend fun getCurrentWeatherByCityName(city: String): WeatherDomainModel {
        val domainModel = domainModelMapper.mapResponseToDomainModel(
            input = api.getCurrentWeatherByCity(city = city)
        )
        return if (domainModel != null && domainModel.isEmptyResponse().not()) {
            domainModel
        } else {
            throw EmptyWeatherResponseException(message = resManager.getString(R.string.empty_weather_response))
        }
    }

    override suspend fun getDailyForecast16(query: String): Any {
        return ""
    }
}