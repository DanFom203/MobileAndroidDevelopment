package com.itis.android_homework.data.repository


import com.itis.android_homework.R
import com.itis.android_homework.data.exceptions.EmptyWeatherResponseException
import com.itis.android_homework.data.mapper.FiveDayForecastDomainModelMapper
import com.itis.android_homework.data.mapper.WeatherDomainModelMapper
import com.itis.android_homework.data.remote.OpenWeatherApi
import com.itis.android_homework.domain.model.forecastmodel.FiveDayForecastDomainModel
import com.itis.android_homework.domain.model.forecastmodel.isEmptyResponse
import com.itis.android_homework.domain.model.weathermodel.WeatherDomainModel
import com.itis.android_homework.domain.model.weathermodel.isEmptyResponse
import com.itis.android_homework.domain.repository.WeatherRepository
import com.itis.android_homework.utils.ResManagerImpl
import javax.inject.Inject


class WeatherRepositoryImpl @Inject constructor(
    private val api: OpenWeatherApi,
    private val domainModelMapper: WeatherDomainModelMapper,
    private val cityDomainModelMapper: FiveDayForecastDomainModelMapper,
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

    override suspend fun get5DayForecast(longitude: Float, latitude: Float): FiveDayForecastDomainModel {
        val cityDomainModel = cityDomainModelMapper.mapResponseToDomainModel(
            input = api.get5DayForecast(longitude = longitude,latitude = latitude)
        )
        return if (cityDomainModel != null && cityDomainModel.isEmptyResponse().not()) {
            cityDomainModel
        } else {
            throw EmptyWeatherResponseException(message = resManager.getString(R.string.empty_weather_response))
        }
    }

}