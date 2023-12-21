package com.itis.android_homework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    val movieId: String,
    val title: String,
    val year: Int,
    val description: String,
    @ColumnInfo(name = "image_url")
    val imgUrl: String?
)