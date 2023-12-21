package com.itis.android_homework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itis.android_homework.db.dao.MovieDao
import com.itis.android_homework.db.dao.RatingDao
import com.itis.android_homework.db.dao.UserDao
import com.itis.android_homework.db.dao.UsersFavoriteMoviesDao
import com.itis.android_homework.db.entity.MovieEntity
import com.itis.android_homework.db.entity.RatingEntity
import com.itis.android_homework.db.entity.UserEntity
import com.itis.android_homework.db.entity.UsersFavouriteMoviesEntity

@Database(
    entities = [UserEntity::class, MovieEntity::class, UsersFavouriteMoviesEntity::class, RatingEntity::class],
    version = 1
)

abstract class InceptionDatabase : RoomDatabase() {

    abstract val userDao: UserDao
    abstract val movieDao: MovieDao
    abstract val usersFavouriteMoviesDao: UsersFavoriteMoviesDao
    abstract val ratingDao: RatingDao
}