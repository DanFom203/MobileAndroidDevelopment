package com.itis.android_homework.presentation.di

import androidx.lifecycle.ViewModel
import com.itis.android_homework.di.ViewModelKey
import com.itis.android_homework.presentation.screens.share_with_contacts.ContactViewModel
import com.itis.android_homework.presentation.screens.weatherinfo.WeatherInfoViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class PresentationModule {

    @Provides
    @[IntoMap ViewModelKey(WeatherInfoViewModel::class)]
    fun provideWeatherInfoViewModel(viewModel: WeatherInfoViewModel): ViewModel = viewModel

    @Provides
    @[IntoMap ViewModelKey(ContactViewModel::class)]
    fun provideContactViewModel(viewModel: ContactViewModel): ViewModel = viewModel
}