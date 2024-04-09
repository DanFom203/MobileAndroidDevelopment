package com.itis.android_homework.presentation.screens.weatherdetails

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.itis.android_homework.BuildConfig
import com.itis.android_homework.R
import com.itis.android_homework.databinding.FragmentWeatherDetailsBinding
import com.itis.android_homework.databinding.FragmentWeatherInfoBinding
import com.itis.android_homework.presentation.base.BaseActivity
import com.itis.android_homework.presentation.base.BaseFragment
import com.itis.android_homework.presentation.screens.debugmenu.DebugMenuFragment
import com.itis.android_homework.presentation.screens.weatherinfo.WeatherInfoViewModel
import com.itis.android_homework.utils.ActionType
import com.itis.android_homework.utils.appComponent
import com.itis.android_homework.utils.lazyViewModel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class WeatherDetailsFragment : BaseFragment(R.layout.fragment_weather_details) {

    private val viewBinding: FragmentWeatherDetailsBinding by viewBinding(FragmentWeatherDetailsBinding::bind)

    private val viewModel: WeatherDetailsViewModel by lazyViewModel {
        requireContext().appComponent.weatherDetailsInfoViewModel().create(weatherId = "Some Assisted Value")
    }

    override fun onAttach(context: Context) {
        requireContext().appComponent.inject(fragment = this)
        super.onAttach(context)
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        arguments?.let {
//            viewModel.start(it)
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            observerData()

            weatherScreenActionBtn.setOnClickListener {
                loadingProgressBar.visibility = android.view.View.VISIBLE
//                viewModel.getWeatherInfo(city = cityNameFieldTv.text.toString())
            }
        }
    }

    private fun observerData() {
//        with(viewModel) {
//
//            /** Использование Flow вместе с кастомным extension
//             * @see utils/Extensions
//             * @see BaseFragment
//             **/
//
//            currentWeatherFlow.observe { weatherData ->
//                weatherData?.let {
//                    with(viewBinding) {
//                        weatherTempTv.text = buildString {
//                            append(getString(R.string.temperature))
//                            append(" : ")
//                            append(it.mainData.temperature)
//                        }
//
//                        showTempIcon(it.iconData.icon)
//                        loadingProgressBar.visibility = View.GONE
//                    }
//                }
//            }
//
//            lifecycleScope.launch {
//                errorsChannel.consumeEach { error ->
//                    val errorMessage = error.message ?: getString(R.string.unknown_error)
//                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show()
//                    viewBinding.loadingProgressBar.visibility = View.GONE
//                }
//            }
//
//        }
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
        private const val TITLE = "temp"

        fun createBundle(temp: Float): Bundle {
            val bundle = Bundle()
            bundle.putFloat(TITLE, temp)
            return bundle
        }
    }
}