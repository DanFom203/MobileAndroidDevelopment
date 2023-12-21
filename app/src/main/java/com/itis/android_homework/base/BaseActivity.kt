package com.itis.android_homework.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val fragmentContainerId: Int

}