package com.itis.android_homework.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.itis.android_homework.R
import com.itis.android_homework.databinding.Item3HourForecastBinding
import com.itis.android_homework.presentation.holder.ForecastHolder
import com.itis.android_homework.presentation.model.CityWeatherForecastUiModel
import com.itis.android_homework.utils.ResManagerImpl

class ForecastAdapter(
    private val resManager: ResManagerImpl,
) : ListAdapter<CityWeatherForecastUiModel, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<CityWeatherForecastUiModel>() {
        override fun areItemsTheSame(
            oldItem: CityWeatherForecastUiModel,
            newItem: CityWeatherForecastUiModel
        ): Boolean = (oldItem as? CityWeatherForecastUiModel)?.dateText == (newItem as? CityWeatherForecastUiModel)?.dateText

        override fun areContentsTheSame(
            oldItem: CityWeatherForecastUiModel,
            newItem: CityWeatherForecastUiModel
        ): Boolean = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return ForecastHolder(
            binding = Item3HourForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        (holder as? ForecastHolder)?.onBind(cityWeatherForecastUiModel = getItem(position))
    }

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is CityWeatherForecastUiModel -> R.layout.item_3_hour_forecast
            else -> throw IllegalArgumentException(resManager.getString(R.string.unknown_error))
        }
}