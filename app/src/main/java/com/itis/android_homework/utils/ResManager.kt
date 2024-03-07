package com.itis.android_homework.utils

import android.content.Context
import androidx.annotation.StringRes

class ResManager(private val ctx: Context) {

    fun getString(@StringRes res: Int): String = ctx.resources.getString(res)

}