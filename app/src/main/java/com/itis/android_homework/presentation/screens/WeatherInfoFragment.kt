package com.itis.android_homework.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.android_homework.BuildConfig
import com.itis.android_homework.R
import com.itis.android_homework.data.runCatching
import com.itis.android_homework.databinding.FragmentWeatherInfoBinding
import com.itis.android_homework.di.ServiceLocator
import com.itis.android_homework.presentation.base.BaseActivity
import com.itis.android_homework.presentation.base.BaseFragment
import com.itis.android_homework.utils.ActionType
import kotlinx.coroutines.launch

class WeatherInfoFragment : BaseFragment(R.layout.fragment_weather_info) {

    private val viewBinding: FragmentWeatherInfoBinding by viewBinding(FragmentWeatherInfoBinding::bind)
    private var debugClickCount = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            weatherHeaderTv.setOnLongClickListener {
                handleDebugClick()
                true
            }

            weatherScreenActionBtn.setOnClickListener {
                val city = cityNameFieldEt.text.toString()
                lifecycleScope.launch {
                    runCatching(ServiceLocator.exceptionHandlerDelegate) {
                        loadingProgressBar.visibility = View.VISIBLE
                        ServiceLocator.getWeatherUseCase.invoke(city = city)
                    }.onSuccess {
                        weatherTempTv.text = buildString {
                            append(getString(R.string.temperature))
                            append(" : ")
                            append(it.mainData.temperature)
                        }

                        showTempIcon(it.iconData.icon)
                        loadingProgressBar.visibility = View.GONE
                    }.onFailure {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()

                        loadingProgressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun handleDebugClick() {
        debugClickCount++
        if (debugClickCount > 2) {
            enterDebugMenu()
        } else {
            Toast.makeText(requireContext(), "Долгое нажатие: $debugClickCount", Toast.LENGTH_SHORT).show()
        }
    }

    private fun enterDebugMenu() {

        Toast.makeText(requireContext(), "Вход в debug-меню!", Toast.LENGTH_SHORT).show()

        (requireActivity() as? BaseActivity)?.goToScreen(
            actionType = ActionType.REPLACE,
            destination = DebugMenuFragment(),
            isAddToBackStack = true
        )
    }

    private fun showTempIcon(icon: String) {
        viewBinding.imageCv.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load(BuildConfig.OPEN_WEATHER_ICON_URL + icon + ".png")
            .into(viewBinding.weatherIconIv)
    }
}