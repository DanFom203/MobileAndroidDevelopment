package com.itis.android_homework

import android.app.Application
import com.itis.android_homework.di.ServiceLocator

class InceptionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.initData(ctx = this)
    }
}