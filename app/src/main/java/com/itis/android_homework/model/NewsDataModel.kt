package com.itis.android_homework.model

import androidx.annotation.DrawableRes

data class NewsDataModel(
    val newsId: Int,
    val newsTitle: String,
    val newsDetails: String? = null,
    @DrawableRes val newsImage: Int? = null,
    var isLiked: Boolean = false
)