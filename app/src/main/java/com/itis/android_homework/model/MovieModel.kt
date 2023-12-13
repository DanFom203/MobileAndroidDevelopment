package com.itis.android_homework.model

import androidx.annotation.DrawableRes

data class MovieModel (
    val id: String,
    val title: String,
    val year: Int,
    val description: String,
    @DrawableRes val movieImage: Int? = null,
    var isLiked: Boolean = false
)