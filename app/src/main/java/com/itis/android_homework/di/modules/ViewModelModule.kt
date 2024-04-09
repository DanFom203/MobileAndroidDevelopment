package com.itis.android_homework.di.modules

import androidx.lifecycle.ViewModelProvider
import com.itis.android_homework.utils.DaggerViewModelFactory
import dagger.Binds
import dagger.Module

@Module
interface ViewModelModule {

    @Binds
    fun bindDaggerFactory_to_ViewModelFactory(impl: DaggerViewModelFactory): ViewModelProvider.Factory
}