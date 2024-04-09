package com.itis.android_homework.data.di

import com.itis.android_homework.data.local.DatabaseModule
import com.itis.android_homework.data.remote.NetworkModule
import dagger.Module

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
        DataModuleBinder::class,
    ]
)
class DataModule