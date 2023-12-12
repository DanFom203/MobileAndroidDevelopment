package com.itis.android_homework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "films")
data class MovieEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title")
    val title: String,
    val year: Int,
    val description: String
)