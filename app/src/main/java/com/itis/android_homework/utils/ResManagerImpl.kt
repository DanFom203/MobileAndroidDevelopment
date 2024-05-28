package com.itis.android_homework.utils

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import javax.inject.Inject

class ResManagerImpl @Inject constructor (private val ctx: Context) : ResManager {

    override fun getString(@StringRes res: Int): String = ctx.resources.getString(res)

    fun getString(@StringRes res: Int, vararg args: Any?): String {
        return ctx.resources.getString(res, *args)
    }

    override fun getColor(@ColorRes res: Int): Int = ctx.resources.getColor(res, null)

}