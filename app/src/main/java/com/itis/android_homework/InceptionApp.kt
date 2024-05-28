package com.itis.android_homework

import android.app.Application
import com.itis.android_homework.di.components.AppComponent
import com.itis.android_homework.di.components.DaggerAppComponent

class InceptionApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .provideContext(ctx = this)
            .build()
    }
}