package com.itis.android_homework.utils

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResManager {
    fun getString(@StringRes res: Int): String
    fun getColor(@ColorRes res: Int): Int
}