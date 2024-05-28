package com.itis.android_homework.presentation

import android.os.Bundle
import com.itis.android_homework.R
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.presentation.screens.CustomViewFragment
import com.itis.android_homework.utils.ActionType

class MainActivity : BaseActivity() {

    override val fragmentContainerId: Int = R.id.main_activity_container

    override fun goToScreen(
        actionType: ActionType,
        destination: BaseFragment,
        tag: String?,
        isAddToBackStack: Boolean,
    ) {
        supportFragmentManager.beginTransaction().apply {
            when (actionType) {
                ActionType.ADD -> add(fragmentContainerId, destination, tag)
                ActionType.REPLACE -> replace(fragmentContainerId, destination, tag)
                ActionType.REMOVE -> remove(destination)
            }
            if (isAddToBackStack) {
                addToBackStack(null)
            }
        }.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(fragmentContainerId, CustomViewFragment(), null)
                .commit()
        }
    }
}
