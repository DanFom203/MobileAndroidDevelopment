package com.itis.android_homework.data.di

import com.itis.android_homework.data.remote.NetworkModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        DataModuleBinder::class,
    ]
)
class DataModule