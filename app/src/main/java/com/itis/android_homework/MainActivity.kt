package com.itis.android_homework

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.itis.android_homework.base.BaseActivity
import com.itis.android_homework.base.BaseFragment
import com.itis.android_homework.ui.fragments.navigation.CoroutineSettingsFragment
import com.itis.android_homework.ui.fragments.navigation.MainScreenFragment
import com.itis.android_homework.ui.fragments.navigation.NotificationSettingsFragment
import com.itis.android_homework.ui.fragments.viewmodel.NotificationSettingsViewModel
import com.itis.android_homework.utils.ActionType

class MainActivity : AppCompatActivity() {

    private var airplaneModeReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedViewModel: NotificationSettingsViewModel by viewModels()

        val action = intent.getIntExtra("actn", ACTION)
        intentActions(action)

        val controller =
            (supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment)
                .navController

        findViewById<BottomNavigationView>(R.id.main_bottom_navigation).apply {
            setupWithNavController(controller)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkNotificationPermission()
        }


        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        airplaneModeReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val airplaneMode = getAirplaneMode()
                    sharedViewModel.updateAirplaneMode(airplaneMode)
                    supportFragmentManager.findFragmentById(R.id.main_activity_container).apply {
                        if (this is CoroutineSettingsFragment) {
                            this.setButtonAvailability(airplaneMode)
                        }
                        if (this is MainScreenFragment) {
                            this.setButtonAvailability(airplaneMode)
                        }
                    }
                }
            }
        }
        registerReceiver(airplaneModeReceiver, filter)
    }

    fun getAirplaneMode() : Boolean {
        return (Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val action = intent?.getIntExtra("actn", ACTION) ?: ACTION
        intentActions(action)
    }

    private fun intentActions(action: Int) {
        when (action) {
            ACTION_LAUNCH_APP_KEY -> {
                val toastText = "You came here from notification"
                Toast.makeText(this, toastText, Toast.LENGTH_LONG).show()
            }
            ACTION_LAUNCH_SETTINGS_KEY -> {

                val controller =
                    (supportFragmentManager.findFragmentById(R.id.main_activity_container) as NavHostFragment)
                        .navController
                controller.popBackStack()
                controller.navigate(R.id.notificationSettingsFragment)
            }
        }
    }

    private fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestNotificationPermission()
        }
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            POST_NOTIFICATIONS_CODE_KEY
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            POST_NOTIFICATIONS_CODE_KEY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Permission Granted: ${permissions[0]}",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (!shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                        showPermissionDeniedMessage()
                    }
                }
            }
        }
    }


    private fun showPermissionDeniedMessage() {
        val message =
            "You have denied the notification permission multiple times. Please enable it in the app settings."
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Open Settings") { _, _ -> openAppSettings() }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("pckg", packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
    }


    companion object {
        const val ACTION = 0
        private const val POST_NOTIFICATIONS_CODE_KEY = 101
        const val ACTION_LAUNCH_APP_KEY = 102
        const val ACTION_LAUNCH_SETTINGS_KEY = 103
    }
}