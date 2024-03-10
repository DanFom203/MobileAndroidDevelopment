package com.itis.android_homework.presentation

import android.os.Bundle
import com.itis.android_homework.R
import com.itis.android_homework.presentation.base.BaseActivity
import com.itis.android_homework.presentation.base.BaseFragment
import com.itis.android_homework.presentation.screens.WeatherInfoFragment
import com.itis.android_homework.utils.ActionType

class MainActivity : BaseActivity() {

    override val fragmentContainerId: Int = R.id.main_activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    fragmentContainerId,
                    WeatherInfoFragment(),
                )
                .commit()
        }
    }

    override fun goToScreen(
        actionType: ActionType,
        destination: BaseFragment,
        tag: String?,
        isAddToBackStack: Boolean,
    ) {
        supportFragmentManager.beginTransaction().apply {
            when (actionType) {
                ActionType.ADD -> {
                    this.add(fragmentContainerId, destination, tag)
                }

                ActionType.REPLACE -> {
                    this.replace(fragmentContainerId, destination, tag)
                }

                ActionType.REMOVE -> {
                    this.remove(destination)
                }

            }
            if (isAddToBackStack) {
                this.addToBackStack(null)
            }
        }.commit()
    }
}