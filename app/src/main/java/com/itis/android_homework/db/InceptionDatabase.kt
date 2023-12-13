package com.itis.android_homework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itis.android_homework.db.dao.MovieDao
import com.itis.android_homework.db.dao.UserDao
import com.itis.android_homework.db.entity.MovieEntity
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.db.typeconverters.UserGsonTypeConverter

@Database(
    entities = [UserEntity::class, MovieEntity::class],
    version = 1
)
//@TypeConverters(UserTypeConverter::class)
//@TypeConverters(UserGsonTypeConverter::class)
abstract class InceptionDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val movieDao: MovieDao
}