package com.itis.android_homework.domain.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class DomainModule {

    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}