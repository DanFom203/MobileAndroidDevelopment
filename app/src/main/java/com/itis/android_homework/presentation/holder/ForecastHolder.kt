package com.itis.android_homework.presentation.holder

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.databinding.Item3HourForecastBinding
import com.itis.android_homework.presentation.model.CityWeatherForecastUiModel

class ForecastHolder(
    private val binding: Item3HourForecastBinding,
) : RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(cityWeatherForecastUiModel: CityWeatherForecastUiModel) {
        with(binding) {
            val temp = cityWeatherForecastUiModel.main.temp.toString()
            temperatureTv.text = "$temp°C"
            temperatureFeelsLikeTv.text = cityWeatherForecastUiModel.main.feelsLike.toString()
            timestampTv.text = cityWeatherForecastUiModel.dateText
        }
    }
}