package com.itis.android_homework.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.itis.android_homework.utils.ActionType


abstract class BaseActivity : AppCompatActivity() {

    protected abstract val fragmentContainerId: Int

    abstract fun goToScreen(
        actionType: ActionType,
        destination: BaseFragment,
        tag: String? = null,
        isAddToBackStack: Boolean = true,
    )
}