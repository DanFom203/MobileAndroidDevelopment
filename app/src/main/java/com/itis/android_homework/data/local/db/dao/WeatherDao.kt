package com.itis.android_homework.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.itis.android_homework.data.local.db.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_data")
    suspend fun getSavedWeatherData(): WeatherEntity?

}