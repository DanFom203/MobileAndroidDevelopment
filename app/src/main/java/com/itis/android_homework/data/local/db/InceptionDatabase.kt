package com.itis.android_homework.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.android_homework.data.local.db.dao.ApiRequestDao
import com.itis.android_homework.data.local.db.dao.WeatherDao
import com.itis.android_homework.data.local.db.entity.WeatherEntity

@Database(
    entities = [
        WeatherEntity::class
    ],
    version = 1,
)

abstract class InceptionDatabase : RoomDatabase() {

    abstract val weatherDao: WeatherDao
    abstract val apiRequestDao: ApiRequestDao
}