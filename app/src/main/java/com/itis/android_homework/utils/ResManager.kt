package com.itis.android_homework.utils

import androidx.annotation.StringRes

interface ResManager {
    fun getString(@StringRes res: Int): String
}