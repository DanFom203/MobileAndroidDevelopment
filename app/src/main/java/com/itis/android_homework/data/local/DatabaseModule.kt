package com.itis.android_homework.data.local

import android.content.Context
import androidx.room.Room
import com.itis.android_homework.data.local.db.InceptionDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideInceptionDatabase(ctx: Context): InceptionDatabase {
        return Room.databaseBuilder(ctx, InceptionDatabase::class.java, "inception.db").build()
    }

    @Provides
    fun provideWeatherDao(db: InceptionDatabase) = db.weatherDao
}