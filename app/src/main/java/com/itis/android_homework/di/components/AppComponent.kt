package com.itis.android_homework.di.components

import android.content.Context
import com.itis.android_homework.data.di.DataModule
import com.itis.android_homework.di.modules.AppModule
import com.itis.android_homework.di.modules.ViewModelModule
import com.itis.android_homework.domain.di.DomainModule
import com.itis.android_homework.presentation.MainActivity
import com.itis.android_homework.presentation.di.PresentationModule
import com.itis.android_homework.presentation.screens.weatherdetails.WeatherDetailsFragment
import com.itis.android_homework.presentation.screens.weatherdetails.WeatherDetailsViewModel
import com.itis.android_homework.presentation.screens.weatherinfo.WeatherInfoFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        ViewModelModule::class,
    ]
)
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun provideContext(ctx: Context): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: WeatherInfoFragment)
    fun inject(fragment: WeatherDetailsFragment)

    fun weatherDetailsInfoViewModel(): WeatherDetailsViewModel.Factory
}