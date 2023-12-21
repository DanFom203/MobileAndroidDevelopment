package com.itis.android_homework.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.R

class MainActivity : BaseActivity() {

    override val fragmentContainerId: Int = R.id.main_activity_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val controller =
            (supportFragmentManager.findFragmentById(fragmentContainerId) as NavHostFragment)
                .navController

        findViewById<BottomNavigationView>(R.id.main_bnv).apply {
            setupWithNavController(controller)
        }

        controller.addOnDestinationChangedListener { _, destination, _ ->
            handleBottomNavigationViewVisibility(destination)
        }
    }

    private fun handleBottomNavigationViewVisibility(destination: NavDestination) {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.main_bnv)

        val shouldShowBottomNav = when (destination.id) {
            R.id.mainScreenFragment, R.id.addingMovieFragment, R.id.profileFragment -> true
            else -> false
        }

        bottomNavigationView.visibility = if (shouldShowBottomNav) {
            View.VISIBLE
        } else {
            View.GONE
        }

    }

}