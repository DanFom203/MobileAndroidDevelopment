package com.itis.android_homework.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes layout: Int) : Fragment(layout) {
    private fun showMessageInConsole(message: String) {
        println("TEST TAG - $message ${this.javaClass.canonicalName}")
    }
}