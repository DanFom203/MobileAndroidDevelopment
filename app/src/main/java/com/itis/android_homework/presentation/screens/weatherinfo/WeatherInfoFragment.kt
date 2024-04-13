package com.itis.android_homework.presentation.screens.weatherinfo

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.itis.android_homework.R
import com.itis.android_homework.databinding.FragmentWeatherInfoBinding
import com.itis.android_homework.presentation.adapter.WeatherListAdapter
import com.itis.android_homework.presentation.base.BaseFragment
import com.itis.android_homework.presentation.model.WeatherUiModel
import com.itis.android_homework.presentation.screens.weatherdetails.WeatherDetailsFragment
import com.itis.android_homework.utils.ResManagerImpl
import com.itis.android_homework.utils.appComponent
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherInfoFragment : BaseFragment(R.layout.fragment_weather_info) {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var resManager: ResManagerImpl

    private val viewModel: WeatherInfoViewModel by viewModels { factory }

    private val viewBinding: FragmentWeatherInfoBinding by viewBinding(FragmentWeatherInfoBinding::bind)

    private var debugClickCount = 0

    private var citiesWeatherAdapter: WeatherListAdapter? = null

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            loadingProgressBar.visibility = View.VISIBLE
            observerData()
            citiesWeatherAdapter = WeatherListAdapter(
                actionNext = ::onWeatherClick,
                resManager = resManager
            )
            citiesRv.adapter = citiesWeatherAdapter
            weatherHeaderTv.setOnLongClickListener {
                handleDebugClick()
                true
            }

        }
    }

    private fun onWeatherClick(weatherUiModel: WeatherUiModel) {
        findNavController().navigate(
            R.id.action_weatherInfoFragment_to_weatherDetailsFragment,
            WeatherDetailsFragment.createBundle(
                weatherIcon = weatherUiModel.iconData.icon,
                weatherLong = weatherUiModel.coordsData.longitude,
                weatherLat = weatherUiModel.coordsData.latitude,
                cityTemperature = weatherUiModel.mainData.temperature
            )
        )
    }

    private fun observerData() {
        with(viewModel) {

            /** Использование Flow вместе с кастомным extension
             * @see utils/Extensions
             * @see BaseFragment
             **/

            currentWeatherFlow.observe { weatherData ->
                weatherData?.let {
                    with(viewBinding) {
                        val weatherCitiesLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                        citiesRv.layoutManager = weatherCitiesLayoutManager
                        citiesRv.adapter = citiesWeatherAdapter
                        citiesWeatherAdapter?.submitList(it)
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

    private fun handleDebugClick() {
        debugClickCount++
        if (debugClickCount > 2) {
            debugClickCount = 0
            enterDebugMenu()
        } else {
            Toast.makeText(requireContext(),
                buildString {
                    append(getString(R.string.long_tap))
                    append(" : ")
                    append("$debugClickCount")
                },
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun enterDebugMenu() {

        Toast.makeText(requireContext(), getString(R.string.debug_menu_toast), Toast.LENGTH_SHORT).show()

        findNavController().navigate(
            R.id.action_weatherInfoFragment_to_debugMenuFragment
        )
    }

}