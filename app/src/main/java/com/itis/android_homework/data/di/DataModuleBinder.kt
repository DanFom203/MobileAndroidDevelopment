package com.itis.android_homework.data.di

import com.itis.android_homework.data.repository.WeatherRepositoryImpl
import com.itis.android_homework.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModuleBinder {

    @Binds
    fun bindWeatherRepository_to_WeatherRepositoryImpl(repositoryImpl: WeatherRepositoryImpl): WeatherRepository
}