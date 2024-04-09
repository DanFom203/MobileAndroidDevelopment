package com.itis.android_homework.presentation.holder

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.R
import com.itis.android_homework.databinding.ItemCityBinding
import com.itis.android_homework.presentation.model.WeatherUiModel
import com.itis.android_homework.utils.ResManager
import com.itis.android_homework.utils.ResManagerImpl
import javax.inject.Inject

class WeatherHolder(
    private val binding: ItemCityBinding,
    private val actionNext: (WeatherUiModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var weatherUiModel: WeatherUiModel? = null

    init {
        itemView.setOnClickListener {
            weatherUiModel?.also(actionNext)
        }
    }

    @SuppressLint("SetTextI18n")
    fun onBind(weatherUiModel: WeatherUiModel) {
        this.weatherUiModel = weatherUiModel
        with(binding) {
            cityNameTv.text = weatherUiModel.name
            val temp = weatherUiModel.mainData.temperature
            val color = setTempColor(temp)
            tempTv.text = "$temp°C"
            tempTv.setTextColor(color)
        }
    }

    private fun setTempColor(temp: Float): Int {
        var color = 0
        if (temp != null) {
            when (temp) {
                in -100.0..-20.1 -> color = R.color.purple_700
                in -20.0..-0.1 -> color = R.color.teal_200
                0.0F -> color = R.color.green
                in 0.1..20.0 -> color = R.color.orange_light
                in 20.1..100.0 -> color = R.color.red
            }
        }
        return color
    }
}
