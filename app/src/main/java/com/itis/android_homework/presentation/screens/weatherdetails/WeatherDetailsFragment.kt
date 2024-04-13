package com.itis.android_homework.presentation.screens.weatherdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.android_homework.BuildConfig
import com.itis.android_homework.R
import com.itis.android_homework.base.Constants
import com.itis.android_homework.base.Keys
import com.itis.android_homework.databinding.FragmentWeatherDetailsBinding
import com.itis.android_homework.presentation.adapter.ForecastAdapter
import com.itis.android_homework.presentation.base.BaseFragment
import com.itis.android_homework.utils.ResManagerImpl
import com.itis.android_homework.utils.appComponent
import com.itis.android_homework.utils.lazyViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherDetailsFragment : BaseFragment(R.layout.fragment_weather_details) {

    private val viewBinding: FragmentWeatherDetailsBinding by viewBinding(FragmentWeatherDetailsBinding::bind)

    @Inject
    lateinit var resManager: ResManagerImpl

    private var forecastAdapter: ForecastAdapter? = null

    private val viewModel: WeatherDetailsViewModel by lazyViewModel {
        requireContext().appComponent.weatherDetailsInfoViewModel().create(
            arguments?.getString(Keys.WEATHER_ICON_KEY) ?: "",
            arguments?.getFloat(Keys.CITY_LONG_KEY) ?: Constants.EMPTY_FLOAT_DATA,
            arguments?.getFloat(Keys.CITY_LAT_KEY) ?: Constants.EMPTY_FLOAT_DATA,
            arguments?.getFloat(Keys.CITY_TEMPERATURE) ?: Constants.EMPTY_FLOAT_DATA
        )
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            forecastAdapter = ForecastAdapter(
                resManager = resManager
            )
            weatherEvery3HoursRv.adapter = forecastAdapter
            loadingProgressBar.visibility = View.VISIBLE
            viewModel.getWeatherInfo()
            observerData()

            weatherDetailsScreenActionBtn.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun observerData() {
        with(viewModel) {

            cityWeatherFlow.observe { weatherData ->
                weatherData?.let {
                    with(viewBinding) {
                        cityNameFieldTv.text = buildString {
                            append(it.cityData.cityName)
                            append(", ")
                            append(it.cityData.country)
                        }
                        weatherTempTv.text = buildString {
                            append(getString(R.string.temperature))
                            append(" : ")
                            append(it.temperature)
                        }

                        showTempIcon(it.icon)

                        val forecastLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        weatherEvery3HoursRv.layoutManager = forecastLayoutManager
                        weatherEvery3HoursRv.adapter = forecastAdapter
                        forecastAdapter?.submitList(it.list)

                        loadingProgressBar.visibility = View.GONE
                    }
                }
            }

            lifecycleScope.launch {
                errorsChannel.consumeEach { error ->
                    val errorMessage = error.message ?: getString(R.string.unknown_error)
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
                    viewBinding.loadingProgressBar.visibility = View.GONE
                }
            }

        }
    }

    private fun showTempIcon(icon: String) {
        viewBinding.imageCv.visibility = View.VISIBLE
        Glide.with(requireContext())
            .load(
                buildString {
                    append(BuildConfig.OPEN_WEATHER_ICON_URL)
                    append(icon)
                    append(".png")
                })
            .into(viewBinding.weatherIconIv)
    }

    companion object {

        fun createBundle(
            weatherLong: Float,
            weatherLat: Float,
            weatherIcon: String,
            cityTemperature: Float
        ): Bundle {
            return bundleOf(
                Keys.CITY_LONG_KEY to weatherLong,
                Keys.CITY_LAT_KEY to weatherLat,
                Keys.WEATHER_ICON_KEY to weatherIcon,
                Keys.CITY_TEMPERATURE to cityTemperature
            )
        }
    }
}