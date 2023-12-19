package com.itis.android_homework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "users_favourite_films",
    primaryKeys = ["user_id", "movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["movie_id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UsersFavouriteMoviesEntity (
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "movie_id")
    val filmId: String
)