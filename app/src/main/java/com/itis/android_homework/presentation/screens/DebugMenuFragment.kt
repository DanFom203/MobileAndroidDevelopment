package com.itis.android_homework.presentation.screens

import android.os.Build
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.BuildConfig
import com.itis.android_homework.R
import com.itis.android_homework.databinding.FragmentDebugBinding
import com.itis.android_homework.presentation.base.BaseFragment

class DebugMenuFragment : BaseFragment(R.layout.fragment_debug) {
    private val viewBinding: FragmentDebugBinding by viewBinding(FragmentDebugBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            appNameTv.text = buildString {
                append(getString(R.string.app_name_header))
                append(" ")
                append(getString(R.string.app_name))
            }
            baseUrlTv.text = buildString {
                append(getString(R.string.base_url))
                append(" ")
                append(BuildConfig.OPEN_WEATHER_BASE_URL)
            }
            versionTv.text = buildString {
                append(getString(R.string.version))
                append(" ")
                append(BuildConfig.VERSION_NAME)
                append(" (")
                append(BuildConfig.VERSION_CODE)
                append(")")
            }
            deviceInfoTv.text = buildString {
                append(getString(R.string.device))
                append(Build.MANUFACTURER)
                append(" ")
                append(Build.MODEL)
                append("\n")
                append(getString(R.string.android_version))
                append(" ")
                append(Build.VERSION.RELEASE)
                append(" (API ")
                append(Build.VERSION.SDK_INT)
                append(")")
            }

            debugScreenActionBtn.setOnClickListener{
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }
}